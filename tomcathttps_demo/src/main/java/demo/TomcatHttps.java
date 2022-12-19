package demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS;

@SpringBootApplication
public class TomcatHttps {

    public static void main(String[] args) {
        ObjectMapper json = new ObjectMapper();
        json.enable(ALLOW_COMMENTS);
        String s = null;
        try {
            s = json.readValue("/", String.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(s);
//        SpringApplication.run(TomcatHttps.class,args);
    }

}
