package team.jeonghokim.daedongyeojido.infrastructure.redis.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class RedisSerializeFailedException extends DaedongException {
    public static final DaedongException EXCEPTION = new RedisSerializeFailedException();

    private RedisSerializeFailedException() {
        super(ErrorCode.REDIS_SERIALIZE_FAIL);
    }
}
