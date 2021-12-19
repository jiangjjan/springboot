package demo.valueannotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ValueController {

    @Autowired
    UseValue useValue;
    @RequestMapping("value")
    public void test() throws IOException {
        useValue.print();
    }
}
