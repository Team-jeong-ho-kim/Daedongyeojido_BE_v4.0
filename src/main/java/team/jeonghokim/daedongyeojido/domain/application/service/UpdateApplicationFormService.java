package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request.ApplicationFormRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                updateApplicationQuestion(request, applicationForm),
                request.getSubmissionDuration()
        );
    }

    private List<ApplicationQuestion> updateApplicationQuestion(ApplicationFormRequest request, ApplicationForm applicationForm) {
        return Optional.ofNullable(request.getContent())
                .orElseGet(List::of)
                .stream()
                .map(applicationQuestion -> ApplicationQuestion.builder()
                        .applicationForm(applicationForm)
                        .content(applicationQuestion)
                        .build())
                .collect(Collectors.toList());
    }
}
