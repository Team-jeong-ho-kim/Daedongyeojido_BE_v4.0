package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ReviewerType;
import team.jeonghokim.daedongyeojido.domain.clubcreation.exception.ClubCreationApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.clubcreation.facade.CurrentReviewer;

@Component
@RequiredArgsConstructor
public class ClubCreationReviewAccessService {

    public void validateReviewerAccess(ClubCreationApplication application, CurrentReviewer reviewer) {
        if (reviewer.reviewerType() == ReviewerType.ADMIN) {
            return;
        }

        if (reviewer.reviewerType() == ReviewerType.TEACHER
                && application.getTeacher().getId().equals(reviewer.reviewerId())) {
            return;
        }

        throw ClubCreationApplicationAccessDeniedException.EXCEPTION;
    }
}
