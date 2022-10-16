package cm.redis.controller;


import cm.redis.service.MultiCacheTestService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试 increment 的原子性
 */
@RequestMapping("atomic")
@RestController
@RequiredArgsConstructor
public class RedisAtomicController {

    final RedisTemplate<String, Object> redis;

    final RedissonClient redissonClient;
    final MultiCacheTestService multiCacheTestService;

    @GetMapping("inr")
    public Object autoInr() {
        String key = "inrKey";
        BoundValueOperations<String, Object> ops = redis.boundValueOps(key);
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threadList.add(new Thread(ops::increment));
        }

        threadList.parallelStream().forEach(Thread::start);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ops.get();

    }
    @GetMapping("list")
    public Object get(){
        return multiCacheTestService.listTest();
    }


}
