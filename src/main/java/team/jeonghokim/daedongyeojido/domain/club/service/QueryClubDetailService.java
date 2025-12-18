package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryClubDetailService {

    private final ClubFacade clubFacade;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public QueryClubDetailResponse execute(Long clubId) {
        Club club = clubFacade.getClubById(clubId);
        List<User> clubMembers = userRepository.findAllByClub(club);

        return QueryClubDetailResponse.of(club, clubMembers);
    }
}
