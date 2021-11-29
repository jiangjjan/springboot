package batch;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JSONController {

    @RequestMapping("get")
    public Object get(){

        return "ok";
    }

}
