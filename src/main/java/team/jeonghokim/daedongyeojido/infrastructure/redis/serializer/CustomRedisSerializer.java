package team.jeonghokim.daedongyeojido.infrastructure.redis.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.RedisSerializer;
import team.jeonghokim.daedongyeojido.infrastructure.redis.exception.RedisDeserializeFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.redis.exception.RedisSerializeFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;

@RequiredArgsConstructor
public class CustomRedisSerializer implements RedisSerializer<SchedulerPayload> {

    private final ObjectMapper objectMapper;

    @Override
    public byte[] serialize(SchedulerPayload payload) {
        try {
            if (payload == null) return null;
            return objectMapper.writeValueAsBytes(payload);
        } catch (Exception e) {
            throw RedisSerializeFailedException.EXCEPTION;
        }
    }

    @Override
    public SchedulerPayload deserialize(byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) return null;
            return objectMapper.readValue(bytes, SchedulerPayload.class);
        } catch (Exception e) {
            throw RedisDeserializeFailedException.EXCEPTION;
        }
    }
}
