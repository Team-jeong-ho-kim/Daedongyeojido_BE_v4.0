package team.jeonghokim.daedongyeojido.infrastructure.redis.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class RedisDeserializeFailedException extends DaedongException {
    public static final DaedongException EXCEPTION = new RedisDeserializeFailedException();

    private RedisDeserializeFailedException() {
        super(ErrorCode.REDIS_DESERIALIZE_FAIL);
    }
}
