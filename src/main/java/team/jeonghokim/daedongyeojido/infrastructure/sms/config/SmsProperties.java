package team.jeonghokim.daedongyeojido.infrastructure.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "solapi-sms")
public record SmsProperties(
        String accessKey,
        String secretKey,
        String from,
        String url
) {
}
