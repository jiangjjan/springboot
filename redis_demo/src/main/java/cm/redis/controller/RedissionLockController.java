package cm.redis.controller;

import lombok.RequiredArgsConstructor;
import org.redisson.ScanResult;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("redission")
@RequiredArgsConstructor
public class RedissionLockController {

    final RedissonClient redissonClient;
    String key = "lockKey";

    @GetMapping("lock")
    public void lock(){
        List<Thread> list =new ArrayList<>();
        for(int i=0;i<100;i++) {
            list.add(
                    new Thread(() -> {
                RLock lock = redissonClient.getLock(key);
                boolean b = lock.tryLock();
                if (b) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-=-=-=-=-=-=-=-=-=-=--=-==-=---=-=-=-=-=-=-=tryLock success=============");
                    lock.forceUnlock();
                }
            }));
        }

        list.parallelStream().forEach(Thread::start);

    }

    @GetMapping("annotationLock")
    public void annotationLock(){
        List<Thread> list =new ArrayList<>();
        for(int i=0;i<100;i++) {
            list.add(
                    new Thread(() -> {
                        RLock lock = redissonClient.getLock(key);
                        boolean b = lock.tryLock();
                        if (b) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("-=-=-=-=-=-=-=-=-=-=--=-==-=---=-=-=-=-=-=-=tryLock success=============");
                            lock.forceUnlock();
                        }
                    }));
        }

        list.parallelStream().forEach(Thread::start);

    }
}
