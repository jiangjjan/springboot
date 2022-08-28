package cm.redis.multi;

import cm.redis.mapper.TestMapper;
import cm.redis.model.Test;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheResolver = "multiCache",cacheNames = "multiCache")
@RequiredArgsConstructor
@Slf4j
public class MultiCacheService {

    final TestMapper testMapper;

    @Cacheable(key = "'multiCacheList'")
    public List<Test> list() {
        log.info("list");
        return testMapper.listTest();
    }

}
