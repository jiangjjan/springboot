package cm.redis.service;

import cm.redis.mapper.TestMapper;
import cm.redis.model.Test;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig( cacheNames = "MultiCacheTestService")
public class MultiCacheTestService {


	final TestMapper testMapper;


	@Cacheable(key = "'allList'", unless = "#result.size()==0", cacheResolver = "multiCache")
	public List<Test> listTest() {
		log.info("exec  cache listTest");
		return testMapper.listTest();
	}
}
