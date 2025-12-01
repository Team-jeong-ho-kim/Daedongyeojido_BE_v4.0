package team.jeonghokim.daedongyeojido.infrastructure.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    @Bean
    public SmsInterceptor smsFeignInterceptor(SmsInterceptor interceptor) {
        return interceptor;
    }
}
