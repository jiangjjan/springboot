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
import org.springframework.scheduling.annotation.EnableAsync;

import static cm.redis.config.RedisKey.Consist.defaultKey;

@Aspect
@EnableAsync
@Configuration
@Slf4j
@RequiredArgsConstructor
public class RedisLockHandler {


    final RedissonClient redissonClient;

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object redisKey(ProceedingJoinPoint proceedingJoinPoint) {

        log.info("start Scheduled {}", proceedingJoinPoint.getSignature());
        Object proceed = null;

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        RedisKey redisKey = signature.getMethod().getAnnotation(RedisKey.class);

        String key = signature.toLongString();
        if (redisKey != null && !defaultKey.equals(key)) {
            key = redisKey.value();
        }

        RLock lock = redissonClient.getLock(key);
        try {
            if (lock.tryLock()) {
                if (redisKey != null)
                    Thread.sleep(redisKey.delayTime());
                proceed = proceedingJoinPoint.proceed();
            }
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        } finally {
            lock.forceUnlock();
            log.info("end exec {}  ", proceedingJoinPoint.getSignature());
        }

        return proceed;

    }


}
