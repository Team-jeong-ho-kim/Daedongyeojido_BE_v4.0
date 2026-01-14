package team.jeonghokim.daedongyeojido.infrastructure.redis.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.RedisSerializer;
import team.jeonghokim.daedongyeojido.infrastructure.redis.exception.RedisDeserializeFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.redis.exception.RedisSerializeFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerSmsPayload;

@RequiredArgsConstructor
public class CustomRedisSerializer implements RedisSerializer<SchedulerSmsPayload> {

    private final ObjectMapper objectMapper;

    @Override
    public byte[] serialize(SchedulerSmsPayload payload) {
        try {
            if (payload == null) return null;
            return objectMapper.writeValueAsBytes(payload);
        } catch (Exception e) {
            throw RedisSerializeFailedException.EXCEPTION;
        }
    }

    @Override
    public SchedulerSmsPayload deserialize(byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) return null;
            return objectMapper.readValue(bytes, SchedulerSmsPayload.class);
        } catch (Exception e) {
            throw RedisDeserializeFailedException.EXCEPTION;
        }
    }
}
