package security.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@SpringBootConfiguration
@ConfigurationProperties(prefix = "sys.config")
@Slf4j
@Data
public class PasswordEncoderConfig {


    String key;

    // 注入SpringContext后, 会自动使用,也可以在配置里单独配置
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("config key is :{}",key);
        return new Pbkdf2PasswordEncoder(key);
    }

}
