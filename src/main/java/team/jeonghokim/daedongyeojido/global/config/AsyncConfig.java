package team.jeonghokim.daedongyeojido.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@EnableRetry
public class AsyncConfig implements AsyncConfigurer {

    private static final String PREFIX = "Async-";
    private static final String LARGE_SMS_PREFIX = "Large-Scale-SMS-";
    private static final String LARGE_ALARM_PREFIX = "Large-Scale-ALARM-";

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(5);
        executor.setThreadNamePrefix(PREFIX);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

    private ThreadPoolTaskExecutor schedulerExecutor(String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(200);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(20);
        executor.initialize();
        return executor;
    }

    @Bean(name = "largeScaleSmsExecutor")
    public Executor smsSchedulerExecutor() {
        return schedulerExecutor(LARGE_SMS_PREFIX);
    }

    @Bean(name = "largeScaleAlarmExecutor")
    public Executor alarmSchedulerExecutor() {
        return schedulerExecutor(LARGE_ALARM_PREFIX);
    }
}
