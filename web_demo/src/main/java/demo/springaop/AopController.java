package demo.springaop;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aop")
@LogConsumerTime("define value")
public class AopController {

    @RequestMapping("test")
    @SneakyThrows
    public String test() {
        Thread.sleep(2000);
        return "aop ok";
    }

    @RequestMapping("aa")
    public String aa() {
        return "aa aop ok";
    }
}
