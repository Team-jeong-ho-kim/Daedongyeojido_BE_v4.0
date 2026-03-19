package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ReviewerType;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository.ClubCreationApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreation.facade.ClubCreationReviewerFacade;
import team.jeonghokim.daedongyeojido.domain.clubcreation.facade.CurrentReviewer;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.ClubCreationApplicationSummaryResponse;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.QueryClubCreationApplicationListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryClubCreationReviewListService {

    private final ClubCreationApplicationRepository clubCreationApplicationRepository;
    private final ClubCreationReviewerFacade clubCreationReviewerFacade;

    @Transactional(readOnly = true)
    public QueryClubCreationApplicationListResponse execute() {
        CurrentReviewer reviewer = clubCreationReviewerFacade.getCurrentReviewer();

        List<ClubCreationApplicationSummaryResponse> applications = reviewer.reviewerType() == ReviewerType.ADMIN
                ? clubCreationApplicationRepository.findAllSummary()
                : clubCreationApplicationRepository.findAllSummaryByTeacherId(reviewer.reviewerId());

        return QueryClubCreationApplicationListResponse.from(applications);
    }
}
