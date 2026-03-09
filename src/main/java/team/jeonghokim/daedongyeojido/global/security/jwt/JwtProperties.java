package team.jeonghokim.daedongyeojido.global.security.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private final String header;  // Jwt 토큰이 포함될 HTTP 헤더 이름
    private final String prefix;  // Jwt 토큰 앞에 붙는 접두사
    private final String secretKey;  // JWT 토큰을 서명 하고 검증 하는 데 사용 되는 비밀 키
    private final Long accessExpiration;  // access 토큰의 만료 시간
    private final Long refreshExpiration; // refresh 토큰의 만료 시간

    public JwtProperties(String header, String prefix, String secretKey, Long accessExpiration, Long refreshExpiration) {
        this.header = header;
        this.prefix = prefix;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }
}
