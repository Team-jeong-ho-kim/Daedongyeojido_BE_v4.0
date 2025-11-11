package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotOpenException;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.DecideClubDissolveRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class DecideClubDissolveService {

    private final ClubFacade clubFacade;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Transactional
    public void execute(Long clubId, DecideClubDissolveRequest request) {
        if (!request.isDecision()) {
            return;
        }

        Club club = clubFacade.getClubById(clubId);
        validateClubIsOpen(club);

        User clubApplicant = userRepository.findByAccountId(club.getClubApplicant().getAccountId())
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        clubRepository.delete(club);
        clubApplicant.updateRole(Role.STUDENT);
    }

    private void validateClubIsOpen(Club club) {
        if (!club.getIsOpen()) {
            throw ClubNotOpenException.EXCEPTION;
        }
    }
}
