package mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.jms.*;
import javax.validation.constraints.NotEmpty;

@Component
@Slf4j
@Validated
public class ActiveService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    public void producerQueue(@NotEmpty String content) {
        jmsTemplate.send(queue, session -> session.createTextMessage(content));
        jmsTemplate.send(topic, session -> session.createTextMessage(content));
    }

    @JmsListener(destination="queue",containerFactory="queueListener")
    public void consumerA(String mes) {
        log.info("queue consumerA {}",mes);
    }

    @JmsListener(destination="queue",containerFactory="queueListener")
    public void consumerB(String mes) {
        log.info("queue consumerB {}",mes);
    }

    @JmsListener(destination="topic",containerFactory="topicListener")
    public void consumerC(String mes) {
        log.info("topic consumerC {}",mes);
    }

    @JmsListener(destination="topic",containerFactory="topicListener")
    public void consumerD(String mes) {
        log.info("topic consumerD {}",mes);
    }


}
