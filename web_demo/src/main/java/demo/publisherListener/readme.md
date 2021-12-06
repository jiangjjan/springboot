#内置简易的发布订阅事件  
类似于事件通知,发布者发出一个消息,多个订阅者对消息进行处理,依赖spring容器;  
发布者使用 ApplicationContextEvent 接口发布消息; 使用@EventListener注解实现订阅,两个传输数据类型一致;  

发布者: 此处是用Aware接口注入已有的ApplicationEventPublisher   
eg:
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
        this.publishEvent=applicationEventPublisher;
    }

    public static void publish(Object event){
        log.info("publish event :{}",event);
        publishEvent.publishEvent(event); 
    }
}

```
订阅者:  
eg:  
```java

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
@Slf4j
public class Listener {
    @EventListener
    public void sendMessage(SendMessage event){
        log.info("sendMessage thread"+Thread.currentThread().getName());
        System.out.println("listen message:"+event.getMessage());
    }
}

```  
publishEvent.publishEvent(event);  
此处默认为阻塞调用需要等到方法调用结束,可以在listener处添加@Async更改为异步调用方式

##同步方式影响:  
同步
##异步方式影响:  
一旦开始异步执行，方法的异常将不会抛出，只能在方法内部处理;