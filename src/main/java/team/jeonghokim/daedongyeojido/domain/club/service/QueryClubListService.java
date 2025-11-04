package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubMajorRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryClubListService {

    private final ClubRepository clubRepository;
    private final ClubMajorRepository clubMajorRepository;

    @Transactional(readOnly = true)
    public QueryClubListResponse execute() {
        List<QueryClubListResponse.ClubDto> clubs = clubRepository.findAllByIsOpenIsTrue().stream()
                .map(club -> {
                    List<ClubMajor> clubMajors = clubMajorRepository.findAllByClub(club);
                    return QueryClubListResponse.ClubDto.from(club, clubMajors);
                }).toList();

        return new QueryClubListResponse(clubs);
    }
}
