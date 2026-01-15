package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyJoinClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.PassClubRequest;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.exception.ResultDurationNotFoundException;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerAlarmPayload;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerSmsPayload;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PassClubService {

    private final UserFacade userFacade;
    private final SubmissionRepository submissionRepository;
    private final ResultDurationRepository resultDurationRepository;
    private final RedisTemplate<String, SchedulerSmsPayload> smsRedisTemplate;
    private final RedisTemplate<String, SchedulerAlarmPayload> alarmRedisTemplate;

    private static final String RESULT_DURATION_SMS_ZSET = "club:result-duration-sms";
    private static final String RESULT_DURATION_ALARM_ZSET = "club:result-duration-alarm";
    public static final String SEOUL_TIME_ZONE = "Asia/Seoul";

    @Transactional
    public void execute(Long submissionId, PassClubRequest request) {

        User user = userFacade.getCurrentUser();

        Submission submission = submissionRepository.findSubmissionById(submissionId)
                        .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        validate(user, submission);

        submission.applyPassResult(request.isPassed());
        
        saveSMS(submission, request.isPassed());

        saveAlarm(submission, request.isPassed());
    }

    private void validate(User user, Submission submission) {

        if (!user.getClub().getId().equals(submission.getApplicationForm().getClub().getId())) {
            throw ClubAccessDeniedException.EXCEPTION;
        }

        if (!(submission.getUser().getClub() == null)) {
            throw AlreadyJoinClubException.EXCEPTION;
        }
    }

    private void saveSMS(Submission submission, boolean isPassed) {

        ResultDuration resultDuration = resultDurationRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> ResultDurationNotFoundException.EXCEPTION);

        long score = resultDuration.getResultDurationTime()
                .atZone(ZoneId.of(SEOUL_TIME_ZONE))
                .toEpochSecond();

        SchedulerSmsPayload payload = SchedulerSmsPayload.builder()
                .submissionId(submission.getId())
                .phoneNumber(submission.getUser().getPhoneNumber())
                .isPassed(isPassed)
                .clubName(submission.getApplicationForm().getClub().getClubName())
                .build();

        smsRedisTemplate.opsForZSet()
                .add(RESULT_DURATION_SMS_ZSET, payload, score);
    }

    private void saveAlarm(Submission submission, boolean isPassed) {

        ResultDuration resultDuration = resultDurationRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> ResultDurationNotFoundException.EXCEPTION);

        long score = resultDuration.getResultDurationTime()
                .atZone(ZoneId.of(SEOUL_TIME_ZONE))
                .toEpochSecond();

        SchedulerAlarmPayload payload = SchedulerAlarmPayload.builder()
                    .alarmType(isPassed ? AlarmType.CLUB_FINAL_ACCEPTED : AlarmType.CLUB_FINAL_REJECTED)
                    .club(submission.getApplicationForm().getClub())
                    .userId(submission.getUser().getId())
                .build();

        alarmRedisTemplate.opsForZSet()
                .add(RESULT_DURATION_ALARM_ZSET, payload, score);
    }
}
