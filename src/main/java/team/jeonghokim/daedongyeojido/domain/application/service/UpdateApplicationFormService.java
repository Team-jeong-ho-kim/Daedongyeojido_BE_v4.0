package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request.ApplicationFormRequest;

@Service
@RequiredArgsConstructor
public class UpdateApplicationFormService {
    private final ApplicationFormRepository applicationFormRepository;

    @Transactional
    public void execute(Long applicationFormId, ApplicationFormRequest request) {

        ApplicationForm applicationForm = applicationFormRepository.findById(applicationFormId)
                .orElseThrow(() -> ApplicationFormNotFoundException.EXCEPTION);

        applicationForm.update(
                request.getApplicationFormTitle(),
                request.getContent(),
                request.getSubmissionDuration()
        );
    }
}
