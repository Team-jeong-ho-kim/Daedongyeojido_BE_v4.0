package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryApplicationFormDetailService {
    private final ApplicationFormRepository applicationFormRepository;

    @Transactional(readOnly = true)
    public ApplicationFormDetailResponse execute(Long applicationFormId) {

        ApplicationForm applicationForm = applicationFormRepository.findByApplicationFormId(applicationFormId)
                .orElseThrow(() -> ApplicationFormNotFoundException.EXCEPTION);

        return ApplicationFormDetailResponse.of(applicationForm);
    }
}
