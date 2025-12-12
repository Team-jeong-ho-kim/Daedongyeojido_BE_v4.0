package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyJoinClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.PassClubRequest;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Service
@RequiredArgsConstructor
public class PassClubService {

    private final UserFacade userFacade;
    private final SubmissionRepository submissionRepository;
    private final SmsService smsService;

    @Transactional
    public void execute(Long submissionId, PassClubRequest request) {
        User user = userFacade.getCurrentUser();
        Submission submission = submissionRepository.findSubmissionById(submissionId)
                        .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        validate(user, submission);

        send(user, request.isPassed());

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

    private void send(User user, boolean isPassed) {
        smsService.send(user.getPhoneNumber(), isPassed ? Message.CLUB_FINAL_ACCEPTED : Message.CLUB_FINAL_REJECTED);
    }
}
