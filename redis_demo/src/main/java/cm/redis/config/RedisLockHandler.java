package cm.redis.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;


@Aspect
@Configuration
@Slf4j
@RequiredArgsConstructor
public class RedisLockHandler {


    final RedissonClient redissonClient;

    @Around("@annotation(redisLock)||@within(redisLock)")
    public Object execOnce(ProceedingJoinPoint proceedingJoinPoint, RedisLock redisLock) throws Throwable {

        log.info("exec execOnce {}", proceedingJoinPoint.getSignature());
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        RedisLock annotation = signature.getMethod().getAnnotation(RedisLock.class);
        if (Objects.nonNull(annotation)) {
            redisLock = annotation;
        }

        log.debug("annotation:{}", redisLock);
        String key = redisLock.value();
        if (RedisLock.Consist.defaultKey.equals(key))
            key = proceedingJoinPoint.getSignature().toLongString();

        RLock lock = redissonClient.getLock(key);
        try {
            log.info("key:{}",key);
            if (lock.tryLock(redisLock.waitTime(), redisLock.releaseTime(), redisLock.unit())){
                Thread.sleep(redisLock.delayTime());
                return proceedingJoinPoint.proceed();
            }else {
                return null;
            }
        } finally {
            lock.forceUnlock();
            log.info("end execOnce {}", proceedingJoinPoint.getSignature());
        }
    }


}
