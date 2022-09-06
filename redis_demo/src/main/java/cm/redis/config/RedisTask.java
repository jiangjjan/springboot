package cm.redis.config;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

import static cm.redis.config.RedisTask.Consist.defaultKey;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisTask {


    String value() default defaultKey;

    long waitTime() default -1;

    long releaseTime() default -1;

    long delayTime() default 0;

    TimeUnit unit() default TimeUnit.SECONDS;

    interface Consist {
        String defaultKey = "defaultRedisTaskKey";
    }
}
