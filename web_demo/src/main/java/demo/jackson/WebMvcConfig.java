package demo.jackson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Component
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("addConverterFactory");
        registry.addConverterFactory(new EnumConverterFactory());
    }


}