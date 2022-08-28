package cm.redis.controller;

import cm.redis.model.Test;
import io.lettuce.core.Range;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("zset")
@Slf4j
public class BoundZSetOperationsController {


    @Autowired
    RedisTemplate<String, Object> redis;

    @RequestMapping("add")
    public Object createData() {

        for (int i = 1; i <= 100; i++) {
            redis.opsForZSet().add(key, new Test((long) i, "name" + i, null), i);
        }
        return "ok";
    }

    static String key = "zset_key";

    @RequestMapping("range")
    public Object queryDataByRange() {

        ZSetOperations<String, Object> zset = redis.opsForZSet();
        //按分数取数据
        log.info("range :{}",zset.range(key,5,20));
        log.info("range score:{}",zset.rangeByScore(key,2,20));
        log.info("reverseRangeByScore :{}",zset.reverseRangeByScore(key,2,4));

        return zset.reverseRangeWithScores(key,0,6); //按下标取数据 从0开始
    }


}
