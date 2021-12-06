package demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class InterceptorController {

    @GetMapping("interceptor")
    public void controller(){
        log.info("开始调用controller");
    }

}
