package demo.publisherListener;

import demo.publisherListener.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("event")
@Slf4j
public class ListenerController {

    final TestMapper testMapper;

    @GetMapping("sendMessage")
    public Object asyncSendMessage() {
        SendMessage e = new SendMessage("this is asyncSendMessage message");
        Publisher.getPublishEvent().publishEvent(e);
        return 1;
    }

    @GetMapping("doSyncSendMessage")
    public Object doSyncSendMessage(Boolean isError) {
        log.info("exec doSyncSendMessage");
        testMapper.addOne(UUID.randomUUID().toString());
        SynchronousEvent e = new SynchronousEvent("this is  doSyncSendMessage message", isError);
        Publisher.getPublishEvent().publishEvent(e);
        if (isError) {
            throw new RuntimeException("throw exception");
        }
        return 1;
    }

    @GetMapping("list")
    public Object list() {
        SendMessage e = new SendMessage("this is asyncSendMessage message");
        List<SendMessageFlag> list = List.of(e);
        ListWrapperEvent ss = new ListWrapperEvent();
        ss.setData(list);
        Publisher.getPublishEvent().publishEvent(ss);
        return 1;
    }
}
