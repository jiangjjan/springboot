package mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("active")
public class ActiveController {

    @Autowired
    ActiveService activeService;

    @GetMapping("produce")
    public void produceMessage() {
        activeService.producerQueue("producer message");
    }

    @Autowired
    Map<String,ApiMethod<?>> s;

    @GetMapping("test")
    public void test(){
        System.out.println(s);
        s.forEach((k,v)->{
            System.out.printf("%s %s",k,v.getMethod());
        });

    }
}
