package demo.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class AopConfig {

    @Before("@annotation(dbType)||@within(dbType)")
    public void master(JoinPoint proc, DataSource dbType) throws Throwable {

        String name = proc.getSignature().getName();
        Object[] args = proc.getArgs();
        Class<?>[]  type = new Class[args.length];
        for(int i=0;i<args.length;i++){
            type[i]=args[i].getClass();
        }
        Method method = proc.getTarget().getClass().getMethod(name, type);
        DataSource annotation = method.getAnnotation(DataSource.class);
        String db = dbType.value();
        if(annotation!=null)
            db=annotation.value();

        System.out.println(name);
        log.info("current db is {}",db);
        RoutingDataSource.switchDataSource(db);

    }

}
