package cm.redis.config;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * <pre>
 *   redis distributed lock ,when orgCode exists, lock key is 'orgCode:{@link #value()}'
 *  {@link #value()} default value is method long Name
 * </pre>
 *
 * @see ProceedingJoinPoint#toLongString()
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExecOnce {


    /**
     * redis key; default value is method long Name
     *
     * @return key
     */
    String value() default Consist.defaultKey;

    /**
     * redis distributed lock should not change this param
     *
     * @return the time max to wait for getting redis lock
     */
    long waitTime() default -1;

    /**
     * time to release lock
     *
     * @return time to release lock
     */
    long releaseTime() default 30;

    /**
     * TimeUnit default is SECOND
     *
     * @return TimeUnit
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * @return delayTime
     */
    long delayTime() default 0;

    interface Consist {
        String defaultKey = "defaultRedisTaskKey";
    }
}
