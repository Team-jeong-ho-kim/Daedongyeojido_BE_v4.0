package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request.ApplicationFormRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateApplicationFormService {
    private final ApplicationFormRepository applicationFormRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(ApplicationFormRequest request) {
        User user = userFacade.getCurrentUser();

        List<ApplicationQuestion> questions = createApplicationQuestion(request);
        LocalDate date = LocalDate.parse(request.getSubmissionDuration());

        applicationFormRepository.save(ApplicationForm.builder()
                .club(user.getClub())
                .user(user)
                .applicationQuestions(questions)
                .submissionDuration(date)
                .build());
    }

    private List<ApplicationQuestion> createApplicationQuestion(ApplicationFormRequest request) {
        return Optional.ofNullable(request.getContent())
                .orElseGet(List::of)
                .stream()
                .map(content -> ApplicationQuestion.builder()
                        .content(content)
                        .build())
                .collect(Collectors.toList());
    }
}
