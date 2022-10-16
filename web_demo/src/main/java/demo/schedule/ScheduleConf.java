package demo.schedule;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class ScheduleConf {

//    @Scheduled(cron = "*/3    *    *    *    *    *")
//    public void task1() throws InterruptedException {
//        Thread.sleep(5000);
//        log.info("每个2秒执行一次 exec task 1");
//    }
//
//    @Scheduled(cron = "*/3    *    *    *    *    *")
//    public void task2() throws InterruptedException {
//
//        Thread.sleep(2000);
//        log.info("每个3秒执行一次 exec task 2");
//    }
}
