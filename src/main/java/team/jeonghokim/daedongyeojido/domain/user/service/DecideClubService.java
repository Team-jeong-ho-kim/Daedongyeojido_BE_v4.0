package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.facade.SubmissionFacade;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideClubRequest;

@Service
@RequiredArgsConstructor
public class DecideClubService {
    private final UserFacade userFacade;
    private final SubmissionFacade submissionFacade;

    @Transactional
    public void execute(Long submissionId, DecideClubRequest request) {
        User user = userFacade.getCurrentUser();

        Submission submission = submissionFacade.getApplicationBySubmissionId(submissionId);
        
        if (request.getIsSelected()) {
            user.selectedClub(submission.getApplicationForm().getClub());
        }
    }
}
