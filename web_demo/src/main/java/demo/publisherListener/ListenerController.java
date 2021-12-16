package demo.publisherListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("event")
@Slf4j
public class ListenerController {

    @GetMapping("sendMessage")
    public Object asyncSendMessage() {
        SendMessage e = new SendMessage("this is asyncSendMessage message");
        Publisher.getPublishEvent().publishEvent(e);
        return 1;
    }

    @Resource
    TestMapper testMapper;

    @GetMapping("doSyncSendMessage")
    public Object doSyncSendMessage(Boolean isError) throws Exception {
        log.info("exec doSyncSendMessage");
        testMapper.addOne(UUID.randomUUID().toString());
        SynchronousEvent e = new SynchronousEvent("this is  doSyncSendMessage message",isError);
        Publisher.getPublishEvent().publishEvent(e);
        if(isError){
            throw new Exception("throw exception");
        }
        return 1;
    }
}
