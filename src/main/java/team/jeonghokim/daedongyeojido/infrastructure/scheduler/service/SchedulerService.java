package team.jeonghokim.daedongyeojido.infrastructure.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final RedisTemplate<String, SchedulerPayload> smsRedisTemplate;
    private final SmsService smsService;
    private final ResultDurationRepository resultDurationRepository;

    public static final String RESULT_DURATION_ZSET = "club:result-duration";
    public static final String SEOUL_TIME_ZONE = "Asia/Seoul";

    @Scheduled(fixedRate = 10_000)
    public void execute() {

        log.info("⏱️ [Scheduler] 실행됨");

        // 1) 발표기간 설정이 안 되어 있을 경우
        ResultDuration resultDuration = resultDurationRepository.findTopByOrderByIdDesc()
                .orElse(null);

        if (resultDuration == null) {
            log.info("⚠️ [Scheduler] 발표기간(ResultDuration)이 아직 설정되지 않음 → 대기");
            return;
        }

        // 2) 발표시간이 되지 않은 경우
        long now = Instant.now().getEpochSecond();
        long duration = resultDuration.getResultDuration()
                .atZone(ZoneId.of(SEOUL_TIME_ZONE))
                .toEpochSecond();


        if (now < duration) {
            log.info("⏳ [Scheduler] 아직 발표시간 아님 — now={}, duration={}", now, duration);
            return;
        }

        // 3) 발표시간이 되었고, Redis에서 메시지 조회
        Set<SchedulerPayload> messages = smsRedisTemplate.opsForZSet()
                .rangeByScore(RESULT_DURATION_ZSET, 0, now);

        if (messages == null || messages.isEmpty()) {
            log.info("📭 [Scheduler] 보낼 메시지가 없음 (ZSET empty)");
            return;
        }

        log.info("📨 [Scheduler] 총 {}개의 문자 발송 시작!", messages.size());

        // 4) 각 메시지 발송
        for (SchedulerPayload payload : messages) {

            log.info("📤 [Send SMS] phone={} passed={}",
                    payload.phoneNumber(),
                    payload.isPassed()
            );

            smsService.send(
                    payload.phoneNumber(),
                    payload.isPassed() ? Message.CLUB_FINAL_ACCEPTED : Message.CLUB_FINAL_REJECTED
            );

            // 5) Redis에서 제거
            smsRedisTemplate.opsForZSet().remove(RESULT_DURATION_ZSET, payload);
        }

        log.info("✅ [Scheduler] 문자 발송 및 Redis 제거 완료");
    }
}
