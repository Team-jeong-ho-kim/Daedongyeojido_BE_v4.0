package team.jeonghokim.daedongyeojido.infrastructure.scheduler.service;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final RedisTemplate<String, SchedulerPayload> smsRedisTemplate;
    private final SmsService smsService;
    private final ResultDurationRepository resultDurationRepository;

    public static final String RESULT_DURATION_ZSET = "club:result-duration";
    public static final String SEOUL_TIME_ZONE = "Asia/Seoul";

    @Transactional
    @Scheduled(fixedRate = 10_000)
    @SchedulerLock(name = "resultDurationExecute", lockAtMostFor = "9s", lockAtLeastFor = "1s")
    public void execute() {

        // 어드민 발표기간 설정 전 스케줄러 대기 상태
        ResultDuration resultDuration = resultDurationRepository.findTopByOrderByIdDesc()
                .orElse(null);

        if (resultDuration == null) {
            return;
        }

        // 발표기간 설정 후 현재 시간과 비교 후 기간이 되지 않았을 경우 대기
        long now = Instant.now().getEpochSecond();
        long duration = resultDuration.getResultDuration()
                .atZone(ZoneId.of(SEOUL_TIME_ZONE))
                .toEpochSecond();

        if (now < duration) {
            return;
        }

        // 발표시간이 되었을때 redis에서 모든 메시지를 가져옴
        Set<SchedulerPayload> messages = smsRedisTemplate.opsForZSet()
                .rangeByScore(RESULT_DURATION_ZSET, 0, now);

        // message가 null일 경우 NPE 방지
        if (messages == null || messages.isEmpty()) {
            return;
        }

        // 문자 발송
        for (SchedulerPayload payload : messages) {
            try {
                smsService.send(
                        payload.phoneNumber(),
                        payload.isPassed() ? Message.CLUB_FINAL_ACCEPTED : Message.CLUB_FINAL_REJECTED
                );

                // 문자 발송 성공 시 제거
                smsRedisTemplate.opsForZSet()
                        .remove(RESULT_DURATION_ZSET, payload);

            } catch (Exception e) {}
        }

        resultDuration.execute();
    }
}
