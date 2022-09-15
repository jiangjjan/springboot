//package cm.redis.task;
//
//import cm.redis.config.ExecOnce;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
////@EnableScheduling
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@ExecOnce
//public class Task {
//
//    @Scheduled(cron = "*/8    *    *    *    *    *")
//    @ExecOnce("keyName")
//    public void taskA() {
//        log.info("exec taskA");
//    }
//
//    @Scheduled(cron = "*/8    *    *    *    *    *")
//    public void taskB() {
//        log.info("exec taskB");
//    }
//
//    @Scheduled(cron = "*/8    *    *    *    *    *")
//    public void taskC() {
//        log.info("exec taskC");
//
//    }
//
//    @Scheduled(cron = "*/8 * * * * ? ")
//    public void taskD() {
//        log.info("exec taskD");
//
//    }
//
//
//
//}
