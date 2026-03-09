package team.jeonghokim.daedongyeojido.domain.club.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;

@Component
@RequiredArgsConstructor
public class ClubFacade {

    private final ClubRepository clubRepository;

    public Club getClubById(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow(() -> ClubNotFoundException.EXCEPTION);
    }
}
