package cm.redis.service;


import cm.redis.mapper.TestMapper;
import cm.redis.model.Test;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig( cacheManager = "caffeine",cacheNames = "testGroup")
public class TestUseAnnotationService {

    final TestMapper testMapper;

    @Cacheable(key = "#p0.id", condition = "#p0.id!=null")
    public Test queryTest(Test param) {
        Optional<Test> test = testMapper.selectOne(param);
        return test.orElse(null);
    }


    @Cacheable(key = "param", condition = "param!=null")
    public Test list(String param) {
        Test p = new Test();
        p.setName(param);
        Optional<Test> test = testMapper.selectOne(p);
        return test.orElse(null);


    }

    @Cacheable(key = "'allList'", unless = "#result.size()==0",cacheResolver = "multiCache")
    public List<Test> listTest() {
        log.info("exec  cache listTest");
        return testMapper.listTest();
    }

    //缓存方法的返回结果
    @CachePut(key = "#result.id", unless = "#result==null")
    public Test addTest(Test param) {
        if (testMapper.insertTest(param) > 0)
            return param;
        else
            return null;
    }

    //组合注解
    @Caching(cacheable = {@Cacheable(cacheNames = "testComposeB", key = "#p0.id", condition = "#p0.id!=null"),
            @Cacheable(cacheNames = "testComposeA", key = "#p0.id", condition = "#p0.id!=null")})
    public Test compose(Test param) {
        Optional<Test> test = testMapper.selectOne(param);
        return test.orElse(null);
    }

}
