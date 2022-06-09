package rabbitdemo;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void produce( String message) {
        rabbitTemplate.convertAndSend("aaaa");
    }

    @RabbitListener
    public void consumer(String message){
        System.err.println(message);
    }
}
