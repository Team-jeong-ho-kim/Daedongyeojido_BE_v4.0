package team.jeonghokim.daedongyeojido.domain.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormNotFoundException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.ApplicantResponse;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QuerySubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuerySubmissionListService {

    private final UserFacade userFacade;
    private final ApplicationFormRepository applicationFormRepository;
    private final SubmissionRepository submissionRepository;

    @Transactional(readOnly = true)
    public QuerySubmissionListResponse execute() {
        User currentUser = userFacade.getCurrentUser();
        ApplicationForm applicationForm = applicationFormRepository.findByClub(currentUser.getClub())
                .orElseThrow(() -> ApplicationFormNotFoundException.EXCEPTION);

        List<ApplicantResponse> applicantResponses =
                submissionRepository.findAllByApplicationFormIdWithValidStatuses(applicationForm.getId());

        return new QuerySubmissionListResponse(applicantResponses);
    }
}
