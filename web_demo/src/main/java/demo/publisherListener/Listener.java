package demo.publisherListener;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Listener {

    @EventListener
    @SneakyThrows
    @Async
    public void sendMessage(SendMessage event){
        log.info("sendMessage thread"+Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println("listen message:"+event.getMessage());
    }

    /**
     * 同步方式调用
     * @param event 数据结构
     */
    @EventListener
    @SneakyThrows
    public void doSynchronousTransaction(SynchronousEvent event){
        log.info("sendMessage thread"+Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println("listen message:"+event.getMessage());
    }


}
