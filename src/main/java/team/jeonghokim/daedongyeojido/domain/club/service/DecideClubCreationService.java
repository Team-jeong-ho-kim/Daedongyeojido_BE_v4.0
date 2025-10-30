package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;

@Service
@RequiredArgsConstructor
public class DecideClubCreationService {

    private final ClubRepository clubRepository;

    @Transactional
    public void execute(Long clubId, DecideClubCreationRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        if (request.getIsOpen()) {
            club.clubOpen();
        }
    }
}
