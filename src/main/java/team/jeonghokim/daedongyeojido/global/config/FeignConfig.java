package team.jeonghokim.daedongyeojido.global.config;

import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.jeonghokim.daedongyeojido.global.feign.CustomErrorDecoder;

@Configuration
@EnableFeignClients(basePackages = "team.jeonghokim.daedongyeojido")
public class FeignConfig {

    @Bean
    @ConditionalOnMissingBean(ErrorDecoder.class)
    public ErrorDecoder commonErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
