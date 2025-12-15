package team.jeonghokim.daedongyeojido.infrastructure.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final RedisTemplate<String, SchedulerPayload> smsRedisTemplate;
    private final SmsService smsService;
    private final ResultDurationRepository resultDurationRepository;

    public static final String RESULT_DURATION_ZSET = "club:result-duration";

    @Scheduled(fixedRate = 10_000)
    public void execute() {

        ResultDuration resultDuration = resultDurationRepository.findTopByOrderByIdDesc()
                .orElse(null);

        if (resultDuration == null) return;

        long now = Instant.now().getEpochSecond();
        long duration = resultDuration.getResultDuration().toEpochSecond(ZoneOffset.UTC);

        if (now < duration) return;

        Set<SchedulerPayload> messages = smsRedisTemplate.opsForZSet()
                .rangeByScore(RESULT_DURATION_ZSET, 0, now);

        if (messages == null || messages.isEmpty()) return;

        for (SchedulerPayload payload : messages) {
            smsService.send(
                    payload.phoneNumber(),
                    payload.isPassed() ? Message.CLUB_FINAL_ACCEPTED : Message.CLUB_FINAL_REJECTED
            );

            smsRedisTemplate.opsForZSet()
                    .remove(RESULT_DURATION_ZSET, payload);
        }
    }
}
