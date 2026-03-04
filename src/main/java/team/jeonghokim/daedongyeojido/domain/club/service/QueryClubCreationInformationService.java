package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.ClubCreationInformationResponse;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.ClubDetailDto;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class QueryClubCreationInformationService {

    private final ClubFacade clubFacade;
    private final ClubRepository clubRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public ClubCreationInformationResponse execute(Long clubId) {

        Club club = clubFacade.getClubById(clubId);

        ClubDetailDto clubDetail = clubRepository.findClubDetailById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        User proposer = userFacade.getUserById(club.getClubApplicant().getId());

        return ClubCreationInformationResponse.of(clubDetail, proposer.getUserName(), proposer.getClassNumber());
    }
}
