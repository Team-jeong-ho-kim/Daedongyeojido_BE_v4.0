package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository.ClubCreationApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.ClubCreationApplicationSummaryResponse;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.QueryClubCreationApplicationListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryClubCreationReviewListService {

    private final ClubCreationApplicationRepository clubCreationApplicationRepository;

    @Transactional(readOnly = true)
    public QueryClubCreationApplicationListResponse execute() {
        List<ClubCreationApplicationSummaryResponse> applications = clubCreationApplicationRepository.findAllSummary();

        return QueryClubCreationApplicationListResponse.from(applications);
    }
}
