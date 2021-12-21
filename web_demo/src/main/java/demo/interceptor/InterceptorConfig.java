package demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
@Component
@Slf4j
public class InterceptorConfig {
    @Bean
    public WebMvcConfigurer interceptor(HandlerInterceptor[] handlerInterceptors) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                log.info("开始添加拦截器, size {}",handlerInterceptors.length);
                for (HandlerInterceptor t : handlerInterceptors) {
                    registry.addInterceptor(t);
                }
            }
        };
    }

}
