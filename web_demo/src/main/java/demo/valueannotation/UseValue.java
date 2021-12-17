package demo.valueannotation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Data
@Component
public class UseValue {

    @Value("normalString")
    private String normalStr;

    @Value("system[]")
    private String osName;

    private String otherBeanProperty;

    private String  fromConfig;

    private Resource file;

    @Value("http://www.baidu.com")
    private Resource url;

}
