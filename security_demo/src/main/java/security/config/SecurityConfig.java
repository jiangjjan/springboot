package security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ConfigurationProperties(prefix = "sys.config")
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    String key;

    /**
     * 用来配置用户签名服务，主要是user-details机制，你还可以给予用户赋予角色
     *
     * @param auth 签名管理器构造器,用于构建用户具体权限控制
     * @see org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }


    /**
     * 用来配置拦截保护的请求，比如什么请求放行，什么请求需要验证
     *
     * @param http http安全请求对象
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

    /**
     * 用来配置Filter链
     *
     * @param web Spring Web Security 对象
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
