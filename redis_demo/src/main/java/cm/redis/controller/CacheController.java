package cm.redis.controller;

import cm.redis.model.Test;
import cm.redis.service.LocalCacheTestService;
import cm.redis.service.TestUseAnnotationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("cache")
@Slf4j
public class CacheController {

    final TestUseAnnotationService testService;
    final LocalCacheTestService localCacheTestService;

    @GetMapping("query/test/{id}")
    public Object queryTest(@PathVariable(required = false) Long id) {
        Test p = new Test();
        p.setId(id);
        Test compose = testService.compose(p);
        log.info("redis result :{}", compose);
        return testService.queryTest(p);
    }

    @GetMapping("list")
    public Object listTest() {

        log.info("exec listTest");
        return testService.listTest();
    }

    @GetMapping("local")
    public Object local(String key1) {
        return localCacheTestService.testNull(key1,null);
    }

    @PostMapping("add")
    public Object addTest(@RequestBody(required = false) Test param) {

        if (param == null) {
            param = new Test();
            param.setName(UUID.randomUUID().toString());
        }

        param.setCreateTime(LocalDateTime.now());
        return testService.addTest(param);
    }
}
