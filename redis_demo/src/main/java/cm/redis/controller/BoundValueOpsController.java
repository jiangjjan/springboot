package cm.redis.controller;


import cm.redis.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("value")
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class BoundValueOpsController {

    final RedisTemplate<String, Object> redis;

    @GetMapping("decrement") //减少值,如果值不存在,会初始化该值
    public Object decrement() {
        String key = "sampleNo21121212";

        return redis.boundValueOps(key).decrement();
    }

    @GetMapping("set")
    public void set() {
        String numberKey = "number";
        Long number = 1L;
        redis.boundValueOps(numberKey).set(number);
    }

    @GetMapping("get")
    public User<Boolean> get(String key) {
        if (Objects.isNull(key)) {
            key = "keyUserValue";
        }
        redis.boundValueOps(key).set(new User<>(null,"name", 123, true));
        return (User<Boolean>) redis.boundValueOps(key).get();
    }

    @GetMapping("get-list")
    public Object list() {
        String listKey = "list";
        List<User<Boolean>> list = new ArrayList<>();
        list.add(new User<>(null,"name1", 21, true));
        list.add(new User<>(null,"name1", 21, false));
        list.add(new User<>(null,"name1", 21, true));
        redis.opsForValue().set(listKey, list);
        List<User<Boolean>> o = (List<User<Boolean>>) redis.opsForValue().get(listKey);
        log.info("list:{}", o);
        return o;
    }


    @GetMapping("addmore")
    public void addMore() {
        StopWatch watch = new StopWatch();
        watch.start("addKey");
        for(int i=0;i<10000;i++){
            redis.opsForValue().set("sessionKey"+i, "SessionValue", Duration.of(20, ChronoUnit.SECONDS ));
        }
        watch.stop();
        System.out.println(watch.getTotalTimeSeconds());
        System.out.println(watch.getTotalTimeMillis());
    }
}
