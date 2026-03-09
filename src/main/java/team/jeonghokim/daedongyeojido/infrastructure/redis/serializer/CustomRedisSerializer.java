package team.jeonghokim.daedongyeojido.infrastructure.redis.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.RedisSerializer;
import team.jeonghokim.daedongyeojido.infrastructure.redis.exception.RedisDeserializeFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.redis.exception.RedisSerializeFailedException;

@RequiredArgsConstructor
public class CustomRedisSerializer<T> implements RedisSerializer<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> targetClass;

    @Override
    public byte[] serialize(T payload) {
        try {
            if (payload == null) return null;
            return objectMapper.writeValueAsBytes(payload);
        } catch (Exception e) {
            throw RedisSerializeFailedException.EXCEPTION;
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) return null;
            return objectMapper.readValue(bytes, targetClass);
        } catch (Exception e) {
            throw RedisDeserializeFailedException.EXCEPTION;
        }
    }
}
