package cm.redis.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Aspect
@EnableAsync
@Configuration
@Slf4j
@RequiredArgsConstructor
public class RedisLockHandler {


    final RedissonClient redissonClient;

    @Around("@annotation(redisTask)")
    public Object redisTask(ProceedingJoinPoint proceedingJoinPoint, RedisTask redisTask) {
        Object proceed = null;

        log.info("exec redisTask {}", proceedingJoinPoint.getSignature());

        System.out.println(proceedingJoinPoint.getSignature().toLongString());
        System.out.println(proceedingJoinPoint.getSignature().toShortString());
        System.out.println(proceedingJoinPoint.getSignature().toString());

         log.debug("redis key is :{}", redisTask.value());
        String key = redisTask.value();
        if (RedisTask.Consist.defaultKey.equals(key)) {
            key = proceedingJoinPoint.getSignature().toString();
        }

        RLock lock = redissonClient.getLock(key);
        boolean isExec;
        try {
            if (redisTask.waitTime() == -1) {
                log.debug("tryLock  with no params, exec {}", proceedingJoinPoint.getSignature());
                isExec = lock.tryLock();
            } else {
                log.debug("tryLock  with  params, exec {}", proceedingJoinPoint.getSignature());
                isExec = lock.tryLock(redisTask.waitTime(), redisTask.releaseTime(), redisTask.unit());
            }
            if (isExec) {
                Thread.sleep(redisTask.delayTime());
                proceed = proceedingJoinPoint.proceed();
            }

        } catch (Throwable ex) {
            log.info("exec task exception :{} ", proceedingJoinPoint.getSignature(), ex);
        } finally {
            lock.forceUnlock();
            log.info("end exec redisTask {}", proceedingJoinPoint.getSignature());
        }

        return proceed;

    }


}
