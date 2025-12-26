package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationListResponse;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryApplicationListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryApplicationListService {
    private final SubmissionRepository submissionRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public QueryApplicationListResponse execute() {
        User user = userFacade.getCurrentUser();
        List<ApplicationListResponse> applications = submissionRepository.findAllByUserId(user.getId());
        return QueryApplicationListResponse.from(applications);
    }
}
