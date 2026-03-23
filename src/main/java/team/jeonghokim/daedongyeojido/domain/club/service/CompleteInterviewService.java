package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyJoinClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.club.exception.UserNotInClubException;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.repository.ScheduleRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewAlreadyCompletedException;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewNotScheduledException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class CompleteInterviewService {

    private final UserFacade userFacade;
    private final SubmissionRepository submissionRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void execute(Long submissionId) {
        User user = userFacade.getCurrentUser();
        Submission submission = submissionRepository.findSubmissionById(submissionId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        validate(user, submission);

        submission.markInterviewCompleted();
    }

    private void validate(User user, Submission submission) {
        if (user.getClub() == null) {
            throw UserNotInClubException.EXCEPTION;
        }

        if (!user.getClub().getId().equals(submission.getApplicationForm().getClub().getId())) {
            throw ClubAccessDeniedException.EXCEPTION;
        }

        if (submission.getUser().getClub() != null) {
            throw AlreadyJoinClubException.EXCEPTION;
        }

        if (!scheduleRepository.existsByApplicantAndClub(submission.getUser(), submission.getApplicationForm().getClub())) {
            throw InterviewNotScheduledException.EXCEPTION;
        }

        if (submission.isInterviewCompleted()) {
            throw InterviewAlreadyCompletedException.EXCEPTION;
        }
    }
}
