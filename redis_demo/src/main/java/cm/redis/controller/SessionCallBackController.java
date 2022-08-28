package cm.redis.controller;


import cm.redis.model.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("call")
@RequiredArgsConstructor
public class SessionCallBackController {

    final RedisTemplate<String, Object> redis;

    /**
     * 使用SessionCallBack接口进行操作
     *
     * 内部并没有使用一个连接, 仍然是一个操作一个连接,与书上讲解的有出入;
     *
     * 这种接口只能算是业务上的一种抽象,将多个业务操作合在一个组成一个SessionCallBack, 之后再由 execute 执行,可以对业务操作进行封装
     *
     * @return
     */
    @GetMapping("back")
    public Object sessionCallBack() {

        return redis.execute(new SessionCallback<Integer>() {

            @Override
            public Integer execute(RedisOperations operations) throws DataAccessException {

                StopWatch watch = new StopWatch();
                watch.start("addKey");
                for (int i = 0; i < 10000; i++) {
                    operations.opsForValue().set("sessionKey" + i, "SessionValue", Duration.of(20, ChronoUnit.SECONDS));
                }
                watch.stop();
                System.out.println(watch.prettyPrint());

                operations.opsForList().leftPush("sessionListKey", new Test(12L, "name", LocalDateTime.now()));
                operations.expire("sessionListKey", 10, TimeUnit.MINUTES);
                return 123;
            }
        });
    }


    @GetMapping("pipe")
    public List<Object> pipeLine() {

        return redis.executePipelined(new SessionCallback<>() {

            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                StopWatch watch = new StopWatch();
                watch.start("addKey");
                for (int i = 0; i < 10000; i++) {
                    operations.opsForValue().set("pipeLine" + i, "SessionValue", Duration.of(60, ChronoUnit.SECONDS));
                }
                watch.stop();
                System.out.println(watch.prettyPrint());
                return null;
            }
        });
    }

    List<Test> d = new ArrayList<>();

    @GetMapping("dump")
    public void dump(int param,boolean add) {
        System.out.println(param);
        List<Test> list = new ArrayList<>();
        for (int i = 0; i < param; i++) {
            list.add(new Test(12L, "name", LocalDateTime.now()));
        }
        if(add){
            for (int i = 0; i < param; i++) {
                d.add(new Test(12L, "name", LocalDateTime.now()));
            }
        }

    }
}
