package mq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

@SpringBootConfiguration
public class ActiveMqConfig {


    @Bean(name = "queue")
    public Queue queue() {
        return new ActiveMQQueue("queue");
    }

    @Bean(name = "topic")
    public Topic topic() {
        return new ActiveMQTopic("topic");
    }

    @Bean("queueListener")
    public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(CachingConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    //在Topic模式中，对消息的监听需要对containerFactory进行配置
    @Bean("topicListener")
    public JmsListenerContainerFactory<?> topicJmsListenerContainerFactory(CachingConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

}
