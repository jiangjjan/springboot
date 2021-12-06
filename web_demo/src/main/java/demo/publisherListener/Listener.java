package demo.publisherListener;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
@Slf4j
public class Listener {

    @EventListener
    @SneakyThrows
    public void sendMessage(SendMessage event){
        log.info("sendMessage thread"+Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println("listen message:"+event.getMessage());
    }
}
