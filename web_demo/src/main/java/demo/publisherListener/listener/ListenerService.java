package demo.publisherListener.listener;

import demo.publisherListener.entity.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
@Slf4j
public class ListenerService {

    @EventListener
    @SneakyThrows
    @Async
    public void sendMessage(SendMessageFlag event) {

        log.info("sendMessage thread {}", Thread.currentThread().getName());
        Thread.sleep(3000);
        log.info("sendMessage listener: {}", event.getMessage());

    }

    @EventListener
    @SneakyThrows
    @Async
    public void sendMessage2(SendMessageFlag event) {

        log.info("sendMessage2 thread {}", Thread.currentThread().getName());
        Thread.sleep(3000);
        log.info("sendMessage2 listener: {}", event.getMessage());

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

    @EventListener
    public <P extends SendMessageFlag>void doSendMessageInterface(P fa) {
        log.info("Entity impl interface P extends SendMessageFlag");
    }

    @EventListener
    public void sendImp(SendMessageFlag fa) {
        log.info("Entity impl interface");
    }




    @EventListener
    @SneakyThrows
    @Async
    public void sendMessage2(ListWrapperEvent event) {

        log.info(" ListWrapperEvent {}", Thread.currentThread().getName());
        Thread.sleep(3000);
        log.info("  public void sendMessage2(List<SendMessageFlag> event) {}", event);

    }





}
