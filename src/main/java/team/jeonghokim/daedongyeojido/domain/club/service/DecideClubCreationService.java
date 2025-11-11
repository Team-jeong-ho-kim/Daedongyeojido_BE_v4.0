package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;

@Service
@RequiredArgsConstructor
public class DecideClubCreationService {

    private final UserRepository userRepository;
    private final ClubFacade clubFacade;

    @Transactional
    public void execute(Long clubId, DecideClubCreationRequest request) {
        if (!request.isOpen()) return;
        Club club = clubFacade.getClubById(clubId);
        User user = userRepository.findById(club.getClubApplicant().getId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        club.clubOpen();
        user.approvedClub(club);
    }
}
