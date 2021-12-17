package demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(annotationClass= Repository.class)
@EnableAsync
@EnableTransactionManagement
public class WebMain {

    public static void main(String[] args) {
        SpringApplication.run(WebMain.class, args);
    }
}