package demo.springaop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;


@Aspect
@Configuration
@Slf4j
public class AopConfigure {

    private LogConsumerTime getAnnotatiom(JoinPoint joinpoint, LogConsumerTime param) {
        LogConsumerTime a;
        if (null == param) { // 方法的注解为null 通过类获取
            Class<?> clazz = joinpoint.getTarget().getClass();
            a = clazz.getAnnotation(LogConsumerTime.class); //类与方法两者上面肯定有一个有注解
        } else {
            a = param;
        }
        return a;
    }

//    @Before("@within(org.springframework.web.bind.annotation.RestController)&args(param)")
//    @Order(3)
//    public void orgCode(JoinPoint joinpoint) {
//        LogConsumerTime a = getAnnotatiom(joinpoint);
//        log.info("before exec {},value:{}", joinpoint.getSignature().getName(), a.value());
//    }

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

    ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
    @Order(1)
    @Before("@annotation(param)")
    public void param(JoinPoint joinpoint, Param param) {
        log.info("exec param");
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Object target = joinpoint.getTarget();
        Method method = signature.getMethod();
        EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(target,method, joinpoint.getArgs());
        Object o = expressionEvaluator.getExpressionValue(param.value(),new AnnotatedElementKey(method,target.getClass()),evaluationContext);

        Object o1 = evaluationContext.lookupVariable(param.value());
        System.out.println(o1);
        System.out.println(o);
    }

}
