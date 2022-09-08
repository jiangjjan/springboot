package cm.redis.config;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisKey {


    String value() default Consist.defaultKey;

    long delayTime() default 0;

    interface Consist {
        String defaultKey = "defaultRedisTaskKey";
    }
}
