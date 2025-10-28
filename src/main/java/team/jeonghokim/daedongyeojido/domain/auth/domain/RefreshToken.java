package team.jeonghokim.daedongyeojido.domain.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private String accountId;

    @Indexed
    private String token;

    @TimeToLive
    private Long timeToLive;

    @Builder
    public RefreshToken(String accountId, String token) {
        this.accountId = accountId;
        this.token = token;
        this.timeToLive = 3600L *2;
    }
}
