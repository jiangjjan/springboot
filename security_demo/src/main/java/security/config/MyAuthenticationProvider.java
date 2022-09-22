package security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

// 注入上下文后会自动使用
@RequiredArgsConstructor
public class MyAuthenticationProvider implements AuthenticationProvider {

	final PasswordEncoder passwordEncoder;
	final UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String rawPassword = (String) authentication.getCredentials();

		// 根据用户名获取用户信息
		UserDetails user = userDetailsService.loadUserByUsername(username);
		if (Objects.isNull(user)) {
			throw new BadCredentialsException("用户名不存在");
		} else {
			 if (!this.passwordEncoder.matches(rawPassword, user.getPassword())) {
				throw new BadCredentialsException("登录名或密码错误");
			} else {
				UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
				result.setDetails(authentication.getDetails());
				return result;
			}
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}