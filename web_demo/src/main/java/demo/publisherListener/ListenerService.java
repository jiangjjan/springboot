package demo.publisherListener;

import demo.publisherListener.entity.SendMessage;
import demo.publisherListener.entity.SynchronousEvent;
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
    public void sendMessage(SendMessage event) {

        log.info("sendMessage thread {}", Thread.currentThread().getName());
        Thread.sleep(3000);
        log.info("sendMessage listener: {}", event.getMessage());

    }

    /**
     * 同步方式调用,抛出异常,测试对调用侧的影响;
     *
     * @param event 数据结构
     */
    @EventListener
    @Async
    public void doSynchronousTransaction(SynchronousEvent event) {

        log.info("sendMessage thread {}", Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("doSynchronousTransaction listener: {}", event.getMessage());

    }


}
