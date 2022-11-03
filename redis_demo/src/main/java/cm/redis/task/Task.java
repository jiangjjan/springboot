package cm.redis.task;

import cm.redis.config.ExecOnce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ExecOnce
//@ConditionalOnExpression("#{environment.getProperty('lis.startModel').contains('44')}")
@ConditionalOnExpression("'${lis.startModel}'.contains('44')")
public class Task {


//    @Scheduled(cron = "0 0/30 * * * ? ")
//    @ExecOnce("keyName")
//    public void taskA() {
//        log.info("exec taskA");
//    }

    @Scheduled(cron = "*/8    *    *    *    *    *")
    @ExecOnce("keyName")
    public void taskB() {
        log.info("exec taskB");
    }

    @Scheduled(cron = "*/8    *    *    *    *    *")
    @ExecOnce("keyName")
    public void taskC() {
        log.info("exec taskC");

    }

    @Scheduled(cron = "*/8 * * * * ? ")
    @ExecOnce("keyName")
    public void taskD() {
        log.info("exec taskD");

    }



}
