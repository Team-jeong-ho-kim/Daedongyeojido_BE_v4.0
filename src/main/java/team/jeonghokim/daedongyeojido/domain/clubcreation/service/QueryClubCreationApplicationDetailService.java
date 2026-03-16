package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.facade.ClubCreationApplicationFacade;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.ClubCreationApplicationDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryClubCreationApplicationDetailService {

    private final ClubCreationApplicationFacade clubCreationApplicationFacade;
    private final QueryMyClubCreationApplicationService queryMyClubCreationApplicationService;

    @Transactional(readOnly = true)
    public ClubCreationApplicationDetailResponse execute(Long applicationId) {
        ClubCreationApplication application = clubCreationApplicationFacade.getById(applicationId);
        return queryMyClubCreationApplicationService.toResponse(application);
    }
}
