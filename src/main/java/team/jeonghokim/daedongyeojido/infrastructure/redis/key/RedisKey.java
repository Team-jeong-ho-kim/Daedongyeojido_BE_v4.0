package team.jeonghokim.daedongyeojido.infrastructure.redis.key;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisKey {

    public static final String RESULT_DURATION_SMS_ZSET = "club:result-duration-sms";

    public static final String RESULT_DURATION_ALARM_ZSET = "club:result-duration-alarm";

    public static final String FAILED_ZSET  = "club:result-duration:failed";
}
