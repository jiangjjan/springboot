package security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import security.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Locale;

import static security.config.RoleDefine.*;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    final ObjectMapper json;

    /**
     * 用来配置用户签名服务，主要是user-details机制，你还可以给予用户赋予角色
     * <p>
     * 自定义UserDetail数据查询的来源
     * {@link AuthenticationManagerBuilder#userDetailsService} 设置自定义UserDetail类 ,上下文注入注入这个接口的bean会自动调用
     * 自定义数据验证模式
     * {@link AuthenticationManagerBuilder#authenticationProvider} 上下文注入注入这个接口的bean会自动调用
     *
     * @param auth 签名管理器构造器,用于构建用户具体权限控制
     * @see org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder
     * @see UserDetailsService
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(this.authenticationProvider);
        super.configure(auth);
    }


    /**
     * 用来配置拦截保护的请求，比如什么请求放行，什么请求需要验证
     *
     * session 设置
     * {@link HttpSecurity#sessionManagement()} 获取session 管理器
     * {@link SessionManagementConfigurer#disable()} 关闭session
     *
     * url 鉴权
     * {@link HttpSecurity#authorizeRequests()} 获取鉴权请求
     * <code> 允许所有的请求
     *      http.csrf().disable().
     *                 authorizeRequests().anyRequest().permitAll();
     * </code>
     * @param http http安全请求对象
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement()
                .invalidSessionUrl("/login.html") //session 超时重新登录

                .and()
                .authorizeRequests()
                .antMatchers("**/admin/**").hasRole(admin.getAuthority())
                .antMatchers("**/user/**").hasAnyRole(admin.getAuthority(),user.getAuthority())
                .anyRequest().authenticated() // 所有路劲都需要鉴权

                .and()
                .formLogin()
                .usernameParameter("name") // 表单内账户 key
                .passwordParameter("password") // 表单内密码 key
                .loginPage("/login.html") // 登录页面
                .loginProcessingUrl("/user/login") //  登录处理的url
                .successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                    log.info("login success"); //登陆成功返回json
                    response.setContentType("application/json;charset=utf-8");
                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                    response.getWriter().write(json.writeValueAsString(Result.success("success:" + request.getSession().getId())));
                })
                .failureHandler((request, response, exception) -> {
                    log.info("login fail");  //登陆失败返回json
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(json.writeValueAsString(Result.success("fail:" + exception.getMessage())));
                })
                .permitAll()

                .and()
                .httpBasic()
                .and().headers().addHeaderWriter(new HeaderWriterCopy());

    }

    /**
     * 用来配置Filter链
     *
     * @param web Spring Web Security 对象
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
}
