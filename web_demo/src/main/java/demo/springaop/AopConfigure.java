package demo.springaop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StopWatch;


@Aspect
@Configuration
@Slf4j
public class AopConfigure {

    private LogConsumerTime getAnnotatiom(JoinPoint joinpoint, LogConsumerTime param) {
        LogConsumerTime a;
        if (null == param) { // 方法的注解为null 通过类获取
            Class<?> clazz = joinpoint.getTarget().getClass();
            a = clazz.getAnnotation(LogConsumerTime.class);
        } else {
            a = param;
        }
        return a;
    }

    @Before("@within(param)||@annotation(param)")
    @Order(3)
    public void logTimePointBefore(JoinPoint joinpoint, LogConsumerTime param) {
        LogConsumerTime a = getAnnotatiom(joinpoint, param);
        log.info("before exec {},value:{}", joinpoint.getSignature().getName(), a.value());
    }

    @Around("@within(param)||@annotation(param)")
    @Order(1)
    public Object logTimePointAround(ProceedingJoinPoint joinPoint, LogConsumerTime param) throws Throwable {
        LogConsumerTime a = getAnnotatiom(joinPoint, param);
        log.info("around exec {},value:{}", joinPoint.getSignature().getName(), a.value());
        StopWatch watch = new StopWatch();
        watch.start();
        Object res = joinPoint.proceed();
        watch.stop();
        log.info("consume time is :{} ms", watch.getLastTaskTimeMillis());
        return res;
    }

    @After("@within(param)||@annotation(param)")
    @Order(1)
    public void logTimePointAfter(JoinPoint joinpoint, LogConsumerTime param) {
        LogConsumerTime a = getAnnotatiom(joinpoint, param);
        log.info("after exec {},value:{}", joinpoint.getSignature().getName(), a.value());
    }

    @AfterReturning("@within(param)||@annotation(param)")
    @Order(1)
    public void logTimePointReturn(JoinPoint joinpoint, LogConsumerTime param) {
        LogConsumerTime a = getAnnotatiom(joinpoint, param);
        log.info("AfterReturning exec {},value:{}", joinpoint.getSignature().getName(), a.value());
    }

}
