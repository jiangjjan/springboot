package demo.resttemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Data
@SpringBootConfiguration
public class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(client()));
        int index = 0;

        for (int i = 0; i < restTemplate.getMessageConverters().size(); i++) {
            if (restTemplate.getMessageConverters().get(i) instanceof MappingJackson2HttpMessageConverter)
                index = i;
        }
        restTemplate.getMessageConverters().add(index,converter());
        return restTemplate;
    }

    OkHttpClient client() {
        return new OkHttpClient().newBuilder()
                .connectionPool(new ConnectionPool(500, 300, TimeUnit.SECONDS))
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(20))
                .build();
    }

    MappingJackson2HttpMessageConverter converter() {
        ObjectMapper json = new ObjectMapper();
        json.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        json.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(json);
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        return converter;
    }

    @Test
    public void main() throws IOException {
        RestTemplate restTemplate = restTemplate();

        MultiValueMap<String, Resource> params = new LinkedMultiValueMap<>();
        String file = "https://adiconlimscloud01.oss-cn-hangzhou.aliyuncs.com/report/file/2021-12-24/0efb07ec-f80a-4f8b-9564-279d41faaa37.pdf";
        UrlResource fileSystemResource = new UrlResource(file);
        InputStreamResource ss = new InputStreamResource(fileSystemResource.getInputStream()) {
            @Override
            public String getFilename() {
                return "fileName.pdf";
            }

            @Override
            public long contentLength(){
                return 0;
            }
        };
        params.add("file", fileSystemResource);
        String s = restTemplate.postForObject("http://localhost:8080/lucene/file", params, String.class);
        System.out.println(s);
    }
}
