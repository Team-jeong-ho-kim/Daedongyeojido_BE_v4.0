package team.jeonghokim.daedongyeojido.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.repository.ScheduleRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewScheduleAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewScheduleNotFoundException;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response.QueryInterviewScheduleDetailResponse;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.exception.SubmissionNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class QueryInterviewScheduleDetailService {

    private final ScheduleRepository scheduleRepository;
    private final SubmissionRepository submissionRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public QueryInterviewScheduleDetailResponse execute(Long scheduleId) {
        User user = userFacade.getCurrentUser();
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> InterviewScheduleNotFoundException.EXCEPTION);

        if (!user.getClub().getId().equals(schedule.getClub().getId())) {
            throw InterviewScheduleAccessDeniedException.EXCEPTION;
        }

        Submission submission = submissionRepository.findByUserIdAndClubId(schedule.getApplicant().getId(), schedule.getClub().getId())
                .orElseThrow(() -> SubmissionNotFoundException.EXCEPTION);

        return QueryInterviewScheduleDetailResponse.of(schedule, submission);
    }
}
