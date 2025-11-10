package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;

@Service
@RequiredArgsConstructor
public class DecideClubCreationService {

    private final ClubFacade clubFacade;

    @Transactional
    public void execute(Long clubId, DecideClubCreationRequest request) {
        Club club = clubFacade.getClubById(clubId);

        if (request.isOpen()) {
            club.clubOpen();
        }
    }
}
