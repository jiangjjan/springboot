package cm.redis.controller;

import cm.redis.model.Address;
import cm.redis.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("list")
@RequiredArgsConstructor
@Slf4j
public class BoundListOpsController {

    final RedisTemplate<String, Object> redis;

    @GetMapping("get")
    public User<Address> get() {
        String key = "addressKey";
        redis.opsForList().leftPush(key, new User<>(null,null, 12, new Address("addressName", 123)));
        User<Address> res = (User<Address>) redis.opsForList().index(key, 0);
        log.info("User <Address> : {}", res);
        return res;
    }

    @GetMapping("getG")
    public User<String> getG() {
        String key = "listKeyG";
        return (User<String>) redis.opsForList().index(key, 0);
    }


}
