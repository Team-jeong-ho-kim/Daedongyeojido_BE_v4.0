package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryClubDetailService {

    private final ClubRepository clubRepository;

    @Transactional(readOnly = true)
    public QueryClubDetailResponse execute(Long clubId) {
        return clubRepository.findDetailWithMembersById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
    }
}
