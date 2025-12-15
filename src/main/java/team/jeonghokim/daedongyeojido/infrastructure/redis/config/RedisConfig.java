package team.jeonghokim.daedongyeojido.infrastructure.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import team.jeonghokim.daedongyeojido.infrastructure.redis.serializer.CustomRedisSerializer;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;

@Configuration
@EnableRedisRepositories(
        enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP,
        keyspaceNotificationsConfigParameter = "" //Elasticache는 CONFIG 명령어를 제한 -> 우회
)
@RequiredArgsConstructor
public class RedisConfig {
    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    private final ObjectMapper objectMapper;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, SchedulerPayload> schedulerRedisTemplate(RedisConnectionFactory factory) {

        ObjectMapper redisObjectMapper = objectMapper.copy();
        redisObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        RedisTemplate<String, SchedulerPayload> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new CustomRedisSerializer<>(redisObjectMapper));
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new CustomRedisSerializer<>(redisObjectMapper));
        template.afterPropertiesSet();

        return template;
    }

}
