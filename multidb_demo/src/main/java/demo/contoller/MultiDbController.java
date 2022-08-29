package demo.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("multi")
public class MultiDbController {



    @GetMapping("adsa")
    public String tes(){
        return "asd";
    }
}
