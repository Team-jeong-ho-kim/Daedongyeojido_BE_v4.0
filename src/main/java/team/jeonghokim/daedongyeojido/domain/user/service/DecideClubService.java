package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.facade.SubmissionFacade;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.AlreadySelectClubException;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideClubRequest;

@Service
@RequiredArgsConstructor
public class DecideClubService {
    private final SubmissionFacade submissionFacade;
    private final UserRepository userRepository;

    @Transactional
    public void execute(Long submissionId, DecideClubRequest request) {

        Submission submission = submissionFacade.getApplicationBySubmissionId(submissionId);

        User applicant = userRepository.findById(submission.getUser().getId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (applicant.getClub() != null) {
            throw AlreadySelectClubException.EXCEPTION;
        }
        
        if (request.getIsSelected()) {
            applicant.selectedClub(submission.getApplicationForm().getClub());
        }
    }
}
