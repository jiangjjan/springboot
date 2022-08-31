package demo.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
@ConditionalOnProperty("multidata.enable")
@Order(-1)
public class AopConfig {

    @Before("@annotation(dbType)||@within(dbType)")
    public void master(JoinPoint proc, DataSource dbType)  {
        DataSource annotation = ((MethodSignature) proc.getSignature()).getMethod().getAnnotation(DataSource.class);
        String db = dbType.value();
        if(annotation!=null)
            db=annotation.value();

        log.info("current db is {}",db);
        RoutingDataSource.switchDataSource(db);

    }

}
