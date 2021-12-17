#spring 事件监听  
事件监听也是设计模式中发布-订阅模式、观察者模式的一种实现  
类似于事件通知,发布者发出一个消息,多个订阅者对消息进行处理,依赖spring容器;  
发布者使用 ApplicationContextEvent 接口发布消息; 使用@EventListener或者@TransactionalEventListener注解实现订阅,两个传输数据类型一致;  

使用:  
定义相互通信的数据结构
```java
import lombok.Data;
import lombok.NonNull;

@Data
public class SendMessage {
    @NonNull
    private String message;
}
```
发布者: 此处是用Aware接口注入已有的ApplicationEventPublisher,这里注入好ApplicationEventPublisher实例  
使用时,直接 Publisher.getPublishEvent().publishEvent(event) 就可以;不需要在其它类里面再注入实现  
ApplicationEventPublisher的bean; 也不建议将publishEvent(event)再封装一层,会导致idea没法将监听者与发布者关联起来  

```java

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class Publisher implements ApplicationEventPublisherAware {

    private static ApplicationEventPublisher publishEvent;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publishEvent=applicationEventPublisher;
    }

     public static ApplicationEventPublisher getPublishEvent(){
           return publishEvent;
    }
}

```
监听者:    
```java

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListenerService {

     @EventListener
     @SneakyThrows
     @Async
     public void sendMessage(SendMessage event){
         log.info("sendMessage thread {}",Thread.currentThread().getName());
         Thread.sleep(3000);
         log.info("listen message: {}",event.getMessage());
     }

}

```  
ListenerService.sendMessage(event);  
此处监听者默认为阻塞调用,可以在listener处添加@Async更改为异步调用方式
否则,在存在多个监听者时,会阻塞调用侧方法;

####同步方式影响:  
同步方式相当内部嵌套方法调用,出现异常时会向上抛出,如果调用侧的方法存在事务,会涉及会回滚操作;  

####异步方式影响:  
一旦开始异步执行，监听者内出现异常不会影响调用侧(详情参考线程原理);

###@TransactionalEventListener  
与上面的注解一样,不同之处在于 , 发布者所在的方法必须顺利调用结束,没有抛出任何异常异常(受检异常与运行时异常) ,监听者才会才会执行 

执行顺序问题:  
多个监听者的调用顺序可以使用@Order注解指定,此种方式只有在阻塞调用时有意义