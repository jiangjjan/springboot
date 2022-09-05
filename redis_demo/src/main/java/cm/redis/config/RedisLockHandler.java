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

        log.info("exec redisTask {}",proceedingJoinPoint.getSignature());
        log.debug("redis key is :{}", redisTask.value());
        RLock lock = redissonClient.getLock(redisTask.value());
        boolean isExec;
        try {
            if (redisTask.waitTime() == -1) {
                log.debug("tryLock  with no params, exec {}",proceedingJoinPoint.getSignature());
                isExec = lock.tryLock();
            } else {
                log.debug("tryLock  with  params, exec {}",proceedingJoinPoint.getSignature());
                isExec = lock.tryLock(redisTask.waitTime(), redisTask.releaseTime(), redisTask.unit());
            }
            if (isExec) {
                Thread.sleep(redisTask.delayTime());
                proceed = proceedingJoinPoint.proceed();
            }

            log.debug("end exec {}",proceedingJoinPoint.getSignature());
        } catch (Throwable ex) {
            log.info("exec task {} exception  ",proceedingJoinPoint.getSignature(), ex);
        } finally {
            lock.forceUnlock();
            log.info(" end exec redisTask {}",proceedingJoinPoint.getSignature());
        }

        return proceed;

    }


}
