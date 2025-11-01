package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryClubDetailService {

    private final ClubRepository clubRepository;

    @Transactional(readOnly = true)
    public QueryClubDetailResponse execute(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        return new QueryClubDetailResponse(QueryClubDetailResponse.ClubDto.from(club));
    }
}
