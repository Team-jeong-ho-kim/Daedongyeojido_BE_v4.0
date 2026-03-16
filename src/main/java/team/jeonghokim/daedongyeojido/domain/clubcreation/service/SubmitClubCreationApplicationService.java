package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.clubcreation.exception.CannotModifyClubCreationApplicationException;
import team.jeonghokim.daedongyeojido.domain.clubcreation.facade.ClubCreationApplicationFacade;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class SubmitClubCreationApplicationService {

    private final ClubCreationApplicationFacade clubCreationApplicationFacade;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long applicationId) {
        ClubCreationApplication application = clubCreationApplicationFacade.getById(applicationId);

        if (!application.getApplicant().getId().equals(userFacade.getCurrentUser().getId())) {
            throw CannotModifyClubCreationApplicationException.EXCEPTION;
        }

        if (!(application.getStatus() == ClubCreationApplicationStatus.DRAFT
                || application.getStatus() == ClubCreationApplicationStatus.CHANGES_REQUESTED)) {
            throw CannotModifyClubCreationApplicationException.EXCEPTION;
        }

        application.submit();
    }
}
