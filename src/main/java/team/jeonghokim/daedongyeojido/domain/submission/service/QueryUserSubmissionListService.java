package team.jeonghokim.daedongyeojido.domain.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QueryUserSubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.SubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserSubmissionListService {
    private final UserFacade userFacade;
    private final SubmissionRepository submissionRepository;

    @Transactional(readOnly = true)
    public QueryUserSubmissionListResponse execute() {
        User user = userFacade.getCurrentUser();

        List<SubmissionListResponse> submissionListResponses = submissionRepository.findAllSubmissionByUserId(user.getId());

        return new QueryUserSubmissionListResponse(submissionListResponses);
    }
}
