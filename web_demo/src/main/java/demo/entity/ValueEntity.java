package demo.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ValueEntity {

    @Value("normal Str")
    private String str;

    @Value("#{systemProperties['os.name']}")
    private String osName;

}
