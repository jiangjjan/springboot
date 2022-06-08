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


    public void producerQueue(String content) {
        jmsTemplate.send("dest", session -> session.createTextMessage(content));
    }

    @JmsListener(destination="dest")
    public void consumerA(String mes) {
        log.info("consumerA {}",mes);
    }

    @JmsListener(destination="dest")
    public void consumerB(String mes) {
        log.info("consumerB {}",mes);
    }

}
