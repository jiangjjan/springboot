//package demo.schedule;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Proxy;
//import java.util.Map;
//
//@Component
//@EnableScheduling
//@Slf4j
//public class ScheduleConf {
//
//
//    public void task1() throws InterruptedException {
//        Thread.sleep(5000);
//        log.info("每个2秒执行一次 exec task 1");
//    }
//
//    @Scheduled(fixedDelay = 3000)
//    public void task2() throws InterruptedException {
//
//        Thread.sleep(2000);
//        log.info("每个3秒执行一次 exec task 2");
//    }
//}
