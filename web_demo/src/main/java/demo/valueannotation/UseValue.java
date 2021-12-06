package demo.valueannotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

public class UseValue {

    private String normalStr;

    private String osName;

    private String otherBeanProperty;

    private String  fromConfig;

    private Resource file;

    @Value("http://www.baidu.com")
    private Resource url;

}
