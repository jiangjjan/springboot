package mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.annotation.JmsListeners;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@Slf4j
public class ActiveService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    public void producerQueue(String content) {
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
