package demo.async;

import demo.mybatis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@SpringBootConfiguration
@EnableAsync
@Slf4j
public class AsyncConf implements AsyncConfigurer {

    /**
     * 注入异步线程使用的线程池
     *
     * @return 线程池
     */
    @Lazy
    @Bean
    public Executor cpuPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Async-Pool");
        executor.setTaskDecorator(createTaskDecorator());
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(2000);
        executor.initialize();
        return executor;
    }

    /**
     * 异步线程异常处理
     *
     * @return 异常handler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("exec {}.{} error,param {},exception message {}",
                    method.getDeclaringClass().getName(), method.getName(),
                    Arrays.toString(params), ex);
        };
    }


    public TaskDecorator createTaskDecorator() {
        return runnable -> () -> {
            RequestAttributes context = RequestContextHolder.currentRequestAttributes();
            try {
                RequestContextHolder.setRequestAttributes(context);
                runnable.run();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }

}
