package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.response.RebuildResultDurationQueueResponse;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.SmsHistory;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.enums.SmsHistoryStatus;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.enums.SmsReferenceType;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.repository.SmsHistoryRepository;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerAlarmPayload;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerSmsPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

import java.time.ZoneId;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static team.jeonghokim.daedongyeojido.infrastructure.redis.key.RedisKey.RESULT_DURATION_ALARM_ZSET;
import static team.jeonghokim.daedongyeojido.infrastructure.redis.key.RedisKey.RESULT_DURATION_SMS_ZSET;

@Slf4j
@Service
@RequiredArgsConstructor
public class RebuildResultDurationQueueFromSmsHistoryService {

    private static final ZoneId SEOUL_TIME_ZONE = ZoneId.of("Asia/Seoul");
    private static final Set<SmsHistoryStatus> RESTORE_TARGET_STATUSES = EnumSet.of(
            SmsHistoryStatus.QUEUED,
            SmsHistoryStatus.REQUESTED
    );

    private final SmsHistoryRepository smsHistoryRepository;
    private final SubmissionRepository submissionRepository;
    private final RedisTemplate<String, SchedulerSmsPayload> smsRedisTemplate;
    private final RedisTemplate<String, SchedulerAlarmPayload> alarmRedisTemplate;

    @Transactional
    public RebuildResultDurationQueueResponse execute() {
        long deletedSmsZsetCount = zsetSize(smsRedisTemplate, RESULT_DURATION_SMS_ZSET);
        long deletedAlarmZsetCount = zsetSize(alarmRedisTemplate, RESULT_DURATION_ALARM_ZSET);

        smsRedisTemplate.delete(RESULT_DURATION_SMS_ZSET);
        alarmRedisTemplate.delete(RESULT_DURATION_ALARM_ZSET);

        List<SmsHistory> histories = smsHistoryRepository.findAllByReferenceTypeAndStatusInAndScheduledAtIsNotNull(
                SmsReferenceType.CLUB_RESULT,
                RESTORE_TARGET_STATUSES
        );

        Map<Long, Submission> submissionMap = submissionRepository.findAllById(
                        histories.stream()
                                .map(SmsHistory::getReferenceId)
                                .collect(Collectors.toSet())
                ).stream()
                .collect(Collectors.toMap(Submission::getId, submission -> submission));

        int restoredSmsCount = 0;
        int restoredAlarmCount = 0;
        int skippedCount = 0;

        for (SmsHistory smsHistory : histories) {
            Submission submission = submissionMap.get(smsHistory.getReferenceId());
            Message message = resolveMessageType(smsHistory.getMessageType());

            if (submission == null || message == null) {
                skippedCount++;
                continue;
            }

            boolean isPassed = message == Message.CLUB_FINAL_ACCEPTED;
            long score = smsHistory.getScheduledAt()
                    .atZone(SEOUL_TIME_ZONE)
                    .toEpochSecond();

            SchedulerSmsPayload smsPayload = SchedulerSmsPayload.builder()
                    .smsHistoryId(smsHistory.getId())
                    .submissionId(submission.getId())
                    .phoneNumber(smsHistory.getPhoneNumber())
                    .isPassed(isPassed)
                    .clubName(smsHistory.getClubName())
                    .build();

            SchedulerAlarmPayload alarmPayload = SchedulerAlarmPayload.builder()
                    .userId(submission.getUser().getId())
                    .clubId(submission.getApplicationForm().getClub().getId())
                    .submissionId(submission.getId())
                    .alarmType(isPassed ? AlarmType.CLUB_FINAL_ACCEPTED : AlarmType.CLUB_FINAL_REJECTED)
                    .isPassed(isPassed)
                    .build();

            smsRedisTemplate.opsForZSet().add(RESULT_DURATION_SMS_ZSET, smsPayload, score);
            alarmRedisTemplate.opsForZSet().add(RESULT_DURATION_ALARM_ZSET, alarmPayload, score);
            restoredSmsCount++;
            restoredAlarmCount++;
        }

        log.info(
                "result duration queue rebuilt: deletedSms={}, deletedAlarm={}, restoredSms={}, restoredAlarm={}, skipped={}",
                deletedSmsZsetCount,
                deletedAlarmZsetCount,
                restoredSmsCount,
                restoredAlarmCount,
                skippedCount
        );

        return RebuildResultDurationQueueResponse.builder()
                .deletedSmsZsetCount(deletedSmsZsetCount)
                .deletedAlarmZsetCount(deletedAlarmZsetCount)
                .restoredSmsCount(restoredSmsCount)
                .restoredAlarmCount(restoredAlarmCount)
                .skippedCount(skippedCount)
                .build();
    }

    private Message resolveMessageType(String messageType) {
        try {
            Message message = Message.valueOf(messageType);
            if (message == Message.CLUB_FINAL_ACCEPTED || message == Message.CLUB_FINAL_REJECTED) {
                return message;
            }
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private <T> long zsetSize(RedisTemplate<String, T> redisTemplate, String key) {
        Long size = redisTemplate.opsForZSet().zCard(key);
        return size == null ? 0L : size;
    }
}
