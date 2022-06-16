package rabbitdemo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private  AmqpAdmin amqpAdmin;

    @Autowired
    private  AmqpTemplate amqpTemplate;

    @Autowired
    RabbitMessagingTemplate rabbitMessagingTemplate;

    @Autowired
    ObjectMapper json;

    public void produce(String message) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(message);
    }

    @RabbitListener
    public void consumerA(String message){
        System.err.println("consumerA "+message);
    }

}
