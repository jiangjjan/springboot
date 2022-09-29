package security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import security.service.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;

// 验证码校验器
@Component
@Slf4j
public class VerificationCodeAuthenticationProvider extends DaoAuthenticationProvider {

	final HttpServletRequest request;
	final RedisTemplate<String, Object> redisTemplate;

	public VerificationCodeAuthenticationProvider(HttpServletRequest request, RedisTemplate<String, Object> redisTemplate, PasswordEncoder passwordEncoder
			, UserDetailsServiceImpl userDetailsService) {
		this.request = request;
		this.redisTemplate = redisTemplate;
		super.setUserDetailsService(userDetailsService);
		super.setPasswordEncoder(passwordEncoder);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		log.info("exec VerificationCodeAuthenticationProvider ");
		String requestCode = request.getParameter("captcha");
		String o = (String) redisTemplate.opsForValue().get(request.getRequestedSessionId());

		if (StringUtils.isEmpty(o))
			throw new SecurityConfig.VerificationCodeException("验证码过期");
		if (!o.equals(requestCode)) {
			throw new SecurityConfig.VerificationCodeException("验证码错误");
		}

		return super.authenticate(authentication);

	}

}