package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryClubDetailService {

    private final ClubFacade clubFacade;

    @Transactional(readOnly = true)
    public QueryClubDetailResponse execute(Long clubId) {
        Club club = clubFacade.getClubById(clubId);

        return QueryClubDetailResponse.of(club);
    }
}
