package cm.redis.config;


import cm.redis.config.ExecOnce;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;


@Aspect
@Configuration
@Slf4j
@RequiredArgsConstructor
public class RedisLockHandler {


    final RedissonClient redissonClient;

    @Around("@annotation(execOnce)")
    public Object execOnce(ProceedingJoinPoint proceedingJoinPoint, ExecOnce execOnce) throws Throwable {

        log.info("exec execOnce {}", proceedingJoinPoint.getSignature());
        String key = execOnce.value();
        if (ExecOnce.Consist.defaultKey.equals(key))
            key = proceedingJoinPoint.getSignature().toLongString();

        RLock lock = redissonClient.getLock(key);
        try {

            if (lock.tryLock(execOnce.waitTime(), execOnce.releaseTime(), execOnce.unit()))
                Thread.sleep(execOnce.delayTime());
            return proceedingJoinPoint.proceed();

        } finally {
            lock.forceUnlock();
            log.info("end execOnce {}", proceedingJoinPoint.getSignature());
        }
    }


}
