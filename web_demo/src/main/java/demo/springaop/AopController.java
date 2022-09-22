package demo.springaop;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aop")
@RequiredArgsConstructor
public class AopController {

    final AopService aop;

    @RequestMapping("test")
    @SneakyThrows
    public String test() {
        Thread.sleep(2000);
        return "aop ok";
    }


    @RequestMapping("aa")
    public String aa() {
        aop.param("name",123L);

        return "aa aop ok";
    }
}
