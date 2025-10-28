package team.jeonghokim.daedongyeojido.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtProperties;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
    //JwtProperties 빈으로 등록
}
