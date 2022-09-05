package cm.redis.task;

import cm.redis.config.RedisTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Slf4j
@Service
public class Task {

    @Scheduled(cron = "*/10    *    *    *    *    *")
    @RedisTask("keys")
    public void taskA() {
        log.info("exec taskA");

    }

    @Scheduled(cron = "*/10    *    *    *    *    *")
    @RedisTask("keys")
    public void taskB() {
        log.info("exec taskB");
    }

    @RedisTask("keys")
    @Scheduled(cron = "*/10    *    *    *    *    *")
    public void taskC() {
        log.info("exec taskC");

    }

    @RedisTask("keys")
    @Scheduled(cron = "*/10    *    *    *    *    *")
    public void taskD() {
        log.info("exec taskD");

    }
}
