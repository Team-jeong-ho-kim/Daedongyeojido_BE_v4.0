package team.jeonghokim.daedongyeojido.domain.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationFormNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.ApplicantResponse;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QueryClubSubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryClubSubmissionListService {

    private final UserFacade userFacade;
    private final ApplicationFormRepository applicationFormRepository;
    private final SubmissionRepository submissionRepository;

    @Transactional(readOnly = true)
    public QueryClubSubmissionListResponse execute(Long applicationFormId) {
        User currentUser = userFacade.getCurrentUser();
        ApplicationForm applicationForm = applicationFormRepository.findById(applicationFormId)
                .orElseThrow(() -> ApplicationFormNotFoundException.EXCEPTION);

        if (!currentUser.getClub().getId().equals(applicationForm.getClub().getId())) {
            throw ClubAccessDeniedException.EXCEPTION;
        }

        List<ApplicantResponse> applicants =
                submissionRepository.findAllByApplicationFormIdWithValidStatuses(applicationForm.getId());

        return QueryClubSubmissionListResponse.from(applicants);
    }
}
