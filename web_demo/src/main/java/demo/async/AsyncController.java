package demo.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("async")
@Slf4j
public class AsyncController {

    final AsyncService asyncService;

    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @RequestMapping("test")
    public String test(@RequestHeader(required = false) String xx) {
        System.out.println("header:"+xx);
        asyncService.serviceA();
        asyncService.cpuTask();
        return "ok";
    }


}
