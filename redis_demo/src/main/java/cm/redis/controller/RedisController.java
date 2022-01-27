package cm.redis.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("redis")
@RequiredArgsConstructor
@Slf4j
public class RedisController {

    final RedisTemplate<String, Object> redis;

    @GetMapping("dervalue")
    public void testTemplate() {
        String key = "sampleNo21121212";
        Boolean exist = redis.hasKey(key);
        RedisSerializer<?> valueSerializer = redis.getValueSerializer();
        if (exist) {
            Long decrement = redis.boundValueOps(key).decrement();
            log.info("No :{}", decrement);
        } else {
            redis.opsForValue().set(key, -1, 1, TimeUnit.HOURS);
        }
    }

    @GetMapping("gerlist")
    public void list() {
        String listKey="list";
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(1L);
        list.add(1L);
        redis.opsForValue().set(listKey,list);
        List<Long> o = (List<Long>)redis.opsForValue().get(listKey);
        log.info("list:{}",o);
    }
}
