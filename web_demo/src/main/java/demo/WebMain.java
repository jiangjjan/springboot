package demo;

import demo.valueAnnotation.ItemWrapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@MapperScan(annotationClass= Repository.class)
public class WebMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(WebMain.class, args);
        System.out.println(run.getBean(ItemWrapper.class).getItems());
    }
}