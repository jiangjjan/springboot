package rabbitdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rabbit")
public class RabbitController {

    @Autowired
    RabbitService rabbitService;

    @GetMapping("produce")
    public void test() throws JsonProcessingException {
        rabbitService.produce("message");
    }

}
