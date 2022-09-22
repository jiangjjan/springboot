package demo.valueAnnotation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;

@Data
@Component
@Slf4j
public class UseValue {

    @Value("normalString")
    private String normalStr;

    @Value("#{systemProperties['os.name']}")
    private String osName;

    @Value("#{otherBean.value}")
    private String otherBeanProperty;

    @Value("#{T(demo.valueAnnotation.OtherBean).generate('123&231')}")
    private String random;

    @Value("[${server.port:80}]")
    private String fromConfig;

    @Value("${dingtalk.name:}")
    String name;
    @Value("${dingtalk.name:jdbc:mysql://10.0.11.5}")
    String mysql;

    @Value("classpath:file/file.txt")
    private Resource file;

    @Value("http://www.baidu.com")
    private Resource url;

    @Autowired
    Environment environment;

    @PostConstruct
    public void print() throws IOException {
        log.info("name {}, mysql {}",name,mysql);
        log.info("normal:{}", normalStr);
        log.info("osName,{}", osName);
        log.info("otherBeanProperty,{}", otherBeanProperty);
        log.info("fromConfig:{}", fromConfig);
        log.info("random:{}", random);
        String content = new String(Files.readAllBytes(file.getFile().toPath()));
        InputStream inputStream = file.getInputStream();
        log.info("file:{}", content);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.getInputStream()))) {
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.ready()) {
                stringBuilder.append(reader.readLine());
            }
            log.info("url:{}", stringBuilder.toString());
        }


    }


}
