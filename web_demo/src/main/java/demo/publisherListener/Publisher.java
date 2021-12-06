package demo.publisherListener;

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

    public static void publish(Object event){
        log.info("publish event :{}",event);
        publishEvent.publishEvent(event);
    }
}
