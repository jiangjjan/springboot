package demo.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 包装Jackson 异常转为自己指定的异常
 */
@SpringBootConfiguration
@Slf4j
public class JacksonConfig {


    @Bean
    public WebMvcConfigurer newDefineJsonHttpMessageConverter() {
        return new WebMvcConfigurer() {
            @Override
            public void configureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {

                for (int i = 0; i < converters.size(); i++) {
                    HttpMessageConverter<?> httpMessageConverter = converters.get(i);
                    if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
                        log.info("change MappingJackson2HttpMessageConverter ");
                        converters.remove(i);
                        converters.add(i, new JsonMessageConverter(((MappingJackson2HttpMessageConverter) httpMessageConverter).getObjectMapper()));
                        break;
                    }
                }

            }
        };
    }

    public static class JsonMessageConverter extends MappingJackson2HttpMessageConverter {

        public JsonMessageConverter(ObjectMapper objectMapper) {
            super(objectMapper); // boot 只使用了这个构造器
        }

        @Override
        public Object read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage) throws JsonMessageConverterException {
            try {
                return super.read(type, contextClass, inputMessage);
            } catch (Exception e) {
                throw new JsonMessageConverterException(e, e.getMessage());
            }

        }

        @Override
        protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws JsonMessageConverterException {
            try {
                return super.readInternal(clazz, inputMessage);
            } catch (Exception e) {
                throw new JsonMessageConverterException(e, e.getMessage());
            }
        }

        @Override
        protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws JsonMessageConverterException {
            try {
                super.writeInternal(object, type, outputMessage);
            } catch (Exception e) {
                throw new JsonMessageConverterException(e, e.getMessage());
            }
        }


    }

    public static class JsonMessageConverterException extends RuntimeException {

        JsonMessageConverterException(Throwable cause, String message) {
            super(message, cause);
        }
    }
}
