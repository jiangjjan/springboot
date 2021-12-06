package demo.publisherListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("event")
public class ListenerController {

    @Autowired
    Map<String, Executor> aa;

    @GetMapping("sendMessage")
    public void a(){
        System.out.println(aa);
        SendMessage e = new SendMessage("this is message");
        Publisher.publish(e);
    }
}
