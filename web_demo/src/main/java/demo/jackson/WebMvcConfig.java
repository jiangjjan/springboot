package demo.jackson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("addConverterFactory");
        registry.addConverterFactory(new EnumConverterFactory());
    }

    static class EnumConverterFactory  implements ConverterFactory<String, ConverterBaseEnum> {

        @Override
        public <T extends ConverterBaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
            System.err.println("add EnumConverterFactory");
            return source ->  {
                for (T e: targetType.getEnumConstants()) {
                    if (e.getCovertValue().equals(source)) {
                        return e;
                    }
                }
                return null;
            };
        }
    }


}