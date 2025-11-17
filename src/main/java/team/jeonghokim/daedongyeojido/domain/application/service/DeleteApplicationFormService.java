package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.facade.ApplicationFormFacade;

@Service
@RequiredArgsConstructor
public class DeleteApplicationFormService {
    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationFormFacade applicationFormFacade;

    @Transactional
    public void execute(Long applicationFormId) {

        ApplicationForm applicationForm = applicationFormFacade.getApplicationById(applicationFormId);

        applicationFormRepository.delete(applicationForm);
    }
}
