package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
public class WebMain {

    @Bean
    public WebMvcConfigurer interceptor(HandlerInterceptor[] handlerInterceptors) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                for (HandlerInterceptor t : handlerInterceptors) {
                    registry.addInterceptor(t);
                }
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(WebMain.class, args);
    }
}
