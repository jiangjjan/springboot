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
@RequiredArgsConstructor
@CacheConfig(cacheManager = "caffeine", cacheNames = "testGroup")
@Slf4j
public class LocalCacheTestService {

    final TestMapper testMapper;

    @Cacheable(key = "'allList'", unless = "#result.size()==0")
    public List<Test> listTest() {
        log.error("exec list");
        return testMapper.listTest();
    }

    @Cacheable(key = "'allList'+#key1+#key2",unless = "#result==null")
    public String testNull(String key1,String key2) {
        log.error("exec list");
        if("1".equals(key1))
            return null;
       return key1+key2;
    }
}
