package security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.mapper.UserInfoMapper;
import security.model.UserInfo;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheManager = "redis", cacheResolver = "multiCache", cacheNames = "userInfo")
public class UserDetailsServiceImpl implements UserDetailsService {

	final UserInfoMapper userInfoMapper;

	@Override
	@Cacheable
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("exec loadUserByUsername :{}", username);
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(username);
		userInfo.setDeleted(0);
		userInfo = userInfoMapper.selectUserByName(userInfo);

		log.info("selectUserByName {}", userInfo);

		if (userInfo == null)
			throw new BadCredentialsException("登录名或密码错误");
		return userInfo;
	}


}
