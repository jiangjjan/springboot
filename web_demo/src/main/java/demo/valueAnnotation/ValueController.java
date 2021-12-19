package demo.valueAnnotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ValueController {

    final UseValue useValue;

    public ValueController(UseValue useValue) {
        this.useValue = useValue;
    }

    @RequestMapping("value")
    public void test() throws IOException {
        useValue.print();
    }
}
