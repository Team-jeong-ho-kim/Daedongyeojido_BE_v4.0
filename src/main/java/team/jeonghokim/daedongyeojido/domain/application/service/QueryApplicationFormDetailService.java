package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class QueryApplicationFormDetailService {
    private final UserFacade userFacade;
    private final ApplicationFormRepository applicationFormRepository;

    @Transactional(readOnly = true)
    public ApplicationFormResponse execute(Long clubId) {
        User user = userFacade.getCurrentUser();

        ApplicationForm applicationForm = applicationFormRepository.findByClubId(clubId)
                .orElseThrow(() -> ApplicationFormNotFoundException.EXCEPTION);

        return ApplicationFormResponse.of(applicationForm);
    }
}
