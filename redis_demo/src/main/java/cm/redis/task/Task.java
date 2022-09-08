package cm.redis.task;

import cm.redis.config.RedisKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Slf4j
@Service
@RequiredArgsConstructor
public class Task {

    @Scheduled(cron = "*/8    *    *    *    *    *")
    @RedisKey("task")
    public void taskA() {
        log.info("exec taskA");
    }

    @Scheduled(cron = "*/8    *    *    *    *    *")
    @RedisKey("task")
    public void taskB() {
        log.info("exec taskB");
    }

    @Scheduled(cron = "*/8    *    *    *    *    *")
    @RedisKey("task")
    public void taskC() {
        log.info("exec taskC");

    }

    @Scheduled(cron = "*/8 * * * * ? ")
    @RedisKey("task")
    public void taskD() {
        log.info("exec taskD");

    }



}
