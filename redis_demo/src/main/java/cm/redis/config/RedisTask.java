package cm.redis.config;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisTask {


    String value() default "defaultRedisTaskKey";

    long waitTime() default -1;

    long releaseTime() default -1;

    long delayTime() default 1000;

    TimeUnit unit() default TimeUnit.SECONDS;
}
