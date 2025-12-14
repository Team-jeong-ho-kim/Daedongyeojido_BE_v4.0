package team.jeonghokim.daedongyeojido.domain.club.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyJoinClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.PassClubRequest;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.exception.ResultDurationNotFoundException;
import team.jeonghokim.daedongyeojido.infrastructure.redis.exception.RedisSerializeFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;

import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class PassClubService {

    private final UserFacade userFacade;
    private final SubmissionRepository submissionRepository;
    private final ResultDurationRepository resultDurationRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final SmsService smsService;

    private static final String RESULT_DURATION_ZSET = "club:result-duration";

    @Transactional
    public void execute(Long submissionId, PassClubRequest request) {
        User user = userFacade.getCurrentUser();
        Submission submission = submissionRepository.findSubmissionById(submissionId)
                        .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        saveSMS(submission, request.isPassed());

        validate(user, submission);

        submission.applyPassResult(request.isPassed());
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

        long score = resultDuration.getResultDuration().toEpochSecond(ZoneOffset.UTC);

        SchedulerPayload payload = SchedulerPayload.builder()
                .submissionId(submission.getId())
                .phoneNumber(submission.getUser().getPhoneNumber())
                .isPassed(isPassed)
                .build();

        String json = serializePayload(payload);

        redisTemplate.opsForZSet().add(RESULT_DURATION_ZSET, json, score);
    }

    private String serializePayload(SchedulerPayload payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw RedisSerializeFailedException.EXCEPTION;
        }
    }
}
