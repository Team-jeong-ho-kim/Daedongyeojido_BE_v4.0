package team.jeonghokim.daedongyeojido.domain.application.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormNotFoundException;

@Component
@RequiredArgsConstructor
public class ApplicationFormFacade {
    private final ApplicationFormRepository applicationFormRepository;

    public ApplicationForm getApplicationById(Long applicationId) {
        return applicationFormRepository.findById(applicationId)
                .orElseThrow(() -> ApplicationFormNotFoundException.EXCEPTION);
    }
}
