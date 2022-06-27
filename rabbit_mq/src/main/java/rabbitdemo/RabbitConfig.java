package rabbitdemo;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class RabbitConfig {

    public static  final String  queueFan = "queueFan";
    
    @Bean
    public Queue SayQueue() {
        return new Queue(queueFan);
    }
}
