package demo.listenEvent;

import demo.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListenEvent {

   @EventListener
   @Async
    public void onApplicationEvent(Event event) {
       log.info(event.toString());
    }
}
