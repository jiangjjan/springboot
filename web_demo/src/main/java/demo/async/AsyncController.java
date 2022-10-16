package demo.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.Computable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("async")
@RequiredArgsConstructor
@Slf4j
public class AsyncController {

    final AsyncService asyncService;
    final HttpServletRequest request;


    @RequestMapping("test")
    public String test(@RequestHeader(required = false) String xx) {
        System.out.println("header:" + xx);
        asyncService.serviceA();
        asyncService.cpuTask();
        return "ok";
    }


    @RequestMapping("task")
    public void task(@RequestHeader(required = false) String a) throws Exception {
        System.out.println("@RequestHeader :"+request.getHeader("a"));

    }


    DeferredResult<String> timeOut = new DeferredResult<>();

    {
        timeOut.setErrorResult("time out");
    }

    @GetMapping("deferred")
    public DeferredResult<String> executeSlowTask(String orgCode) throws InterruptedException {
        log.info("Request received");
        DeferredResult<String> deferredResult = new DeferredResult<>(60000L, () -> "time out");

        WebRequestManager.add(orgCode, deferredResult); //队列满的时候抛出异常

        return deferredResult;
    }

    @GetMapping("returnRes")
    public void sendResult() {
        CompletableFuture.supplyAsync(() -> {
            try {
                log.info("Slow task executed");
                Map<String, Queue<DeferredResult<String>>> stringQueueMap = WebRequestManager.getWebClient("1023");

                stringQueueMap.get("1023").forEach(e-> e.setResult("result"));
                return "Task finished";
            } catch (Exception e) {
                e.printStackTrace();
                return "Task exception";
            }
        });
    }


    public static class WebRequestManager {

        private static final Map<String, Queue<DeferredResult<String>>> mapDeque = new ConcurrentHashMap<>();


        public static void add(String orgCode, DeferredResult<String> param) {
            mapDeque.compute(orgCode, (k, v) -> {
              if(v==null)
                  v= new ArrayBlockingQueue<>(1000);
                v.add(param);
                return v;
            });
            log.info("add mapDeque {}",mapDeque);
        }

        //copy 出原来的一份数据
        public static   Map<String, Queue<DeferredResult<String>>> getWebClient(String orgCode) {

            Map<String, Queue<DeferredResult<String>>> copyData = new HashMap<>();

            mapDeque.compute(orgCode,
                    (k, v) -> {
                        if(v==null)
                            v= new ArrayBlockingQueue<>(1000);
                        copyData.put(orgCode,v);
                        return null;
                    });
            log.info("pauseAndCopy mapDeque {}",mapDeque);

            return copyData;

        }
    }

}
