package mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("active")
public class ActiveController {

    @Autowired
    ActiveService activeService;

    @GetMapping("produce")
    public void produceMessage() {
        activeService.producerQueue("producer message");
    }
}
