package rabbitdemo;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static rabbitdemo.RabbitConfig.queueFan;

@Service
@RequiredArgsConstructor
public class RabbitService {

    final RabbitTemplate rabbitTemplate;

    final AmqpAdmin amqpAdmin;

    final AmqpTemplate amqpTemplate;

    final RabbitMessagingTemplate rabbitMessagingTemplate;

    public void produce(String message) {
        rabbitTemplate.convertAndSend(message);
    }

    @RabbitListener(queues = queueFan)
    public void consumerA(String message){
        System.err.println("consumerA "+message);
    }

}
