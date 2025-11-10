package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubApplication;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class DecideClubCreationService {

    private final ClubRepository clubRepository;
    private final ClubApplicationRepository clubApplicationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void execute(Long clubId, DecideClubCreationRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        ClubApplication clubApplication = clubApplicationRepository.findByClubId(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        User user = userRepository.findById(clubApplication.getId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (request.isOpen()) {
            club.clubOpen();
            user.approvedClub(club);
        }
    }
}
