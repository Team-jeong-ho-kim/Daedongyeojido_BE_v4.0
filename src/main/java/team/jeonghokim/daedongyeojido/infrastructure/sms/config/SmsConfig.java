package team.jeonghokim.daedongyeojido.infrastructure.sms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.jeonghokim.daedongyeojido.infrastructure.sms.auth.SignatureProvider;

@Configuration
@RequiredArgsConstructor
public class SmsConfig {

    @Value("${solapi-sms.accessKey}")
    private String accessKey;

    @Value("${solapi-sms.secretKey}")
    private String secretKey;

    private final SignatureProvider signatureProvider;

    @Bean
    public SmsInterceptor smsInterceptor() {
        return new SmsInterceptor(signatureProvider, accessKey, secretKey);
    }
}
