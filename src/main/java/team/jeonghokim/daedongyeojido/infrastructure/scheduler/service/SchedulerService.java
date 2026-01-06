package team.jeonghokim.daedongyeojido.infrastructure.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.admin.service.DecideResultDurationService;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.LargeScaleSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

import java.time.Instant;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final RedisTemplate<String, SchedulerPayload> smsRedisTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public static final String RESULT_DURATION_ZSET = "club:result-duration";
    private final DecideResultDurationService decideResultDurationService;

    public void execute() {

        decideResultDurationService.executeScheduler();

        long now = Instant.now().getEpochSecond();

        Set<SchedulerPayload> payloads =
                smsRedisTemplate.opsForZSet()
                        .rangeByScore(RESULT_DURATION_ZSET, 0, now + 5); // 대규모 데이터 처리로 인한 실행 시간 지연 고려 설정

        log.info("SMS 발송 대상 수 = {}", payloads == null ? 0 : payloads.size());

        if (payloads == null || payloads.isEmpty()) {
            return;
        }

        payloads.forEach(this::publishEvent);
    }

    private void publishEvent(SchedulerPayload payload) {

        eventPublisher.publishEvent(
                LargeScaleSmsEvent.builder()
                        .phoneNumber(payload.phoneNumber())
                        .message(payload.isPassed()
                                ? Message.CLUB_FINAL_ACCEPTED
                                : Message.CLUB_FINAL_REJECTED)
                        .clubName(payload.clubName())
                        .payload(payload)
                        .build()
        );

        log.info("이벤트 발행: submissionId={}, phone={}", payload.submissionId(), payload.phoneNumber());
    }
}
