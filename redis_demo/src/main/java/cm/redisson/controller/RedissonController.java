package cm.redisson.controller;

import cm.redis.model.Test;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@EnableScheduling
@RequestMapping("redisson")
@RequiredArgsConstructor
public class RedissonController {

    final RedissonClient redissonClient;

   static ThreadLocal<Test> testThreadLocal = ThreadLocal.withInitial(Test::new);

    @GetMapping("leaseTime")
    public String leaseTime() throws InterruptedException {
        a();
        System.out.println(testThreadLocal.get());
        return "SUCCESS";

    }

    private void a() {
        Test test = testThreadLocal.get();
        System.out.println("a:"+test);
        test.setName("=================");
    }

    @GetMapping("testLock")
    public void testLock() {

        List<String> con = List.of("a", "b", "c");

        con.parallelStream().forEach(e -> {
            RLock lock = redissonClient.getLock("testLock");
//            try {
//                if (lock.tryLock()) {
//                    try {
//                        Thread.sleep(1000);
//                        System.err.println(e);
//                    } catch (InterruptedException interruptedException) {
//                        interruptedException.printStackTrace();
//                    } finally {
//                        lock.unlock();
//                    }
//                }else{
//                    System.out.println("=======");
//                }
//            } catch (Exception interruptedException) {
//                interruptedException.printStackTrace();
//            }

            try {
                if (lock.tryLock(2, TimeUnit.SECONDS)) {

                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } finally {
                lock.forceUnlock();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.out.println("e");

        });
    }

    @GetMapping("lock")
    public Object autoInr() {
        String key = "testLockKey";
        Num testNum = new Num();
        testNum.lock = redissonClient.getLock("lockKey");
        val threadList = new ArrayList<Thread>();
        int i = 0;
        while (i < 1000) {
            threadList.add(new Thread(testNum::add));
            i++;
        }
        threadList.parallelStream().forEach(Thread::start);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return testNum.total;

    }

    @Scheduled(cron = "0 19 11 ? * *")
    public void test() {
        RLock lock = redissonClient.getLock("test");
        if (lock.tryLock())
            System.out.println("=============================");
        lock.forceUnlock();
    }

    static class Num {
        int total = 0;

        RLock lock;

        public Num() {
        }


        public void add() {

            // RLock为可重入锁 value 为重入的计数 不设置锁的过期时间会自动续期

//            lock.lock();
//            addDelegte();
            total += 1;
//            lock.forceUnlock();

        }

        private void addDelegte() {
//            RLock lock = redissonClient.getLock("lockKey");
            lock.lock();
            total += 1;
            lock.forceUnlock();
        }
    }

}
