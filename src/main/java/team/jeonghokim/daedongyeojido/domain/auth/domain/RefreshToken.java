package team.jeonghokim.daedongyeojido.domain.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@RedisHash
public class RefreshToken {

    @Id
    private String accountId;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long timeToLive;

    public void update(String refreshToken, Long timeToLive) {
        this.refreshToken = refreshToken;
        this.timeToLive = timeToLive;
    }
}
