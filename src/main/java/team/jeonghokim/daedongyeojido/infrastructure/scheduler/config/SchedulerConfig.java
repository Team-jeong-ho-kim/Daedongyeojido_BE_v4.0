package team.jeonghokim.daedongyeojido.infrastructure.scheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private static final String PREFIX = "result-duration-";

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix(PREFIX);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.initialize();
        scheduler.setAwaitTerminationSeconds(10);
        return scheduler;
    }
}
