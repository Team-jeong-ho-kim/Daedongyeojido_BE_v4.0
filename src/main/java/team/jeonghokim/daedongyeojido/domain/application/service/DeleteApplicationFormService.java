package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormNotFoundException;

@Service
@RequiredArgsConstructor
public class DeleteApplicationFormService {
    private final ApplicationFormRepository applicationFormRepository;

    @Transactional
    public void execute(Long applicationFormId) {

        ApplicationForm applicationForm = applicationFormRepository.findById(applicationFormId)
                .orElseThrow(() -> ApplicationFormNotFoundException.EXCEPTION);

        applicationFormRepository.delete(applicationForm);
    }
}
