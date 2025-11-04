package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubLinkRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubMajorRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryClubDetailService {

    private final ClubRepository clubRepository;
    private final ClubMajorRepository clubMajorRepository;
    private final ClubLinkRepository clubLinkRepository;

    @Transactional(readOnly = true)
    public QueryClubDetailResponse execute(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        List<ClubMajor> clubMajors = clubMajorRepository.findAllByClub(club);
        List<ClubLink> clubLinks = clubLinkRepository.findAllByClub(club);

        return QueryClubDetailResponse.of(club, clubMajors, clubLinks);
    }
}
