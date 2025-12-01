package team.jeonghokim.daedongyeojido.infrastructure.sms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.jeonghokim.daedongyeojido.infrastructure.sms.auth.SignatureProvider;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SmsProperties.class)
public class SmsConfig {

    private final SignatureProvider signatureProvider;
    private final SmsProperties properties;

    @Bean
    public SmsInterceptor smsInterceptor() {
        return new SmsInterceptor(signatureProvider, properties.accessKey(), properties.secretKey());
    }
}
