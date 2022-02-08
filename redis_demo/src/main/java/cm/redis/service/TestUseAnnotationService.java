package cm.redis.service;


import cm.redis.mapper.TestMapper;
import cm.redis.model.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "testGroup")
public class TestUseAnnotationService {

    final TestMapper testMapper;

    @Cacheable(key = "#p0.id", condition = "#p0.id!=null")
    public Test queryTest(Test param) {
        Optional<Test> test = testMapper.selectOne(param);
        return test.orElse(null);
    }

    @Cacheable(key = "'allList'", unless = "#result.size()==0")
    public List<Test> listTest() {
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
