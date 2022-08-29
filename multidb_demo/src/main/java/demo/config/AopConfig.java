package demo.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AopConfig {

    @Before("@annotation(master)||@within(master)")
    public void master(JoinPoint proc, DataSource master) throws Throwable {

        log.info("current db is {}",master.value());
        RoutingDataSource.switchDataSource(master.value());

    }

}
