package team.jeonghokim.daedongyeojido.domain.application.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Component
@RequiredArgsConstructor
public class ApplicationFormFacade {
    private final ApplicationFormRepository applicationFormRepository;
    private final ClubFacade clubFacade;
    private final UserFacade userFacade;

    public ApplicationForm getApplicationById(Long applicationId) {

        ApplicationForm applicationForm = applicationFormRepository.findById(applicationId)
                .orElseThrow(() -> ApplicationFormNotFoundException.EXCEPTION);

        User user = userFacade.getCurrentUser();

        if (!applicationForm.getClub().getId().equals(user.getClub().getId())) {
            throw ApplicationFormAccessDeniedException.EXCEPTION;
        }

        return applicationForm;
    }
}
