package demo.valueannotation;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class OtherBean {

    private String value = "defaultValue";
}