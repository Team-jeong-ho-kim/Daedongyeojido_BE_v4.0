package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.facade.ApplicationFormFacade;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormDetailResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class QueryApplicationFormDetailService {
    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationFormFacade applicationFormFacade;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public ApplicationFormDetailResponse execute(Long applicationFormId) {

        User user = userFacade.getCurrentUser();
        ApplicationForm applicationForm = applicationFormFacade.getApplicationById(applicationFormId);

        if (!applicationForm.getClub().getId().equals(user.getClub().getId())) {
            throw ApplicationFormAccessDeniedException.EXCEPTION;
        }

        return ApplicationFormDetailResponse.of(applicationForm);
    }
}
