package security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final UserDetailsService userDetailsService;
    final AuthenticationProvider authenticationProvider;

    /**
     * 用来配置用户签名服务，主要是user-details机制，你还可以给予用户赋予角色
     *
     * 自定义UserDetail数据查询的来源
     *{@link AuthenticationManagerBuilder#userDetailsService}
     * 自定义数据验证模式
     * {@link AuthenticationManagerBuilder#authenticationProvider}
     *
     * @param auth 签名管理器构造器,用于构建用户具体权限控制
     * @see org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder
     * @see UserDetailsService
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider);
    }


    /**
     * 用来配置拦截保护的请求，比如什么请求放行，什么请求需要验证
     *
     * @param http http安全请求对象
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                authorizeRequests().anyRequest().permitAll();
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
