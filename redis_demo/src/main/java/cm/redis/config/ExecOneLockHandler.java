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
public class ExecOneLockHandler {


    final RedissonClient redissonClient;

    @Around("@annotation(execOnce)||@within(execOnce)")
    public Object execOnce(ProceedingJoinPoint proceedingJoinPoint, ExecOnce execOnce) throws Throwable {

        log.info("exec execOnce {}", proceedingJoinPoint.getSignature());
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        ExecOnce annotation = signature.getMethod().getAnnotation(ExecOnce.class);
        if (Objects.nonNull(annotation)) {
            execOnce = annotation;
        }

        log.debug("annotation:{}",execOnce);
        String key = execOnce.value();
        if (ExecOnce.Consist.defaultKey.equals(key))
            key = proceedingJoinPoint.getSignature().toLongString();

        RLock lock = redissonClient.getLock(key);
        try {
            log.info("key:{}",key);
            if (lock.tryLock(execOnce.waitTime(), execOnce.releaseTime(), execOnce.unit())){
                Thread.sleep(execOnce.delayTime());
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
