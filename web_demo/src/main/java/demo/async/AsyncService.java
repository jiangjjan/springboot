package demo.async;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AsyncService {

    @Async
    @SneakyThrows
    public void serviceA() {
        Thread.sleep(2000);
        log.info("exec async task");
    }

    @Async
    @SneakyThrows
    @Transactional
    public void cpuTask() {
        Thread.sleep(2000);
        log.info("exec cpu task");
        throw new RuntimeException("async runtime exception");
    }
}
