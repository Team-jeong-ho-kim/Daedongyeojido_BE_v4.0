package team.jeonghokim.daedongyeojido.domain.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
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
        // 1. 조회하려는 유저의 동아리 정보를 가져오기
        User currentUser = userFacade.getCurrentUser();

        // 2. 유저의 동아리 정보를 통해서 ApplicationForm 가져오기
        ApplicationForm applicationForm = applicationFormRepository.findByClub(currentUser.getClub());

        // 3. ApplicationForm에 해당하는 Submission 엔티티 중 ApplicationStatus가 SUBMITTED, ACCEPTED, REJECTED 인 엔티티를 모두 가져와서 list로 반환하기
        List<ApplicantResponse> applicantResponses = submissionRepository.findAllByApplicationFormWithValidStatuses(applicationForm);

        return new QuerySubmissionListResponse(applicantResponses);
    }
}
