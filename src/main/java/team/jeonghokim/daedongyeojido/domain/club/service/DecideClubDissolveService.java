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

import java.util.List;

@Service
@RequiredArgsConstructor
public class DecideClubDissolveService {

    private final ClubFacade clubFacade;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Transactional
    public void execute(Long clubId, DecideClubDissolveRequest request) {
        if (!request.isDecision()) return;
        Club club = clubFacade.getClubById(clubId);

        if (!club.getIsOpen()) {
            throw ClubNotOpenException.EXCEPTION;
        }

        // 동아리 해체 수락 시 해체되는 동아리의 동아리원들 권한을 STUDENT로 수정
        List<User> clubMembers = userRepository.findAllByClub(club);
        clubMembers.forEach(member -> member.leaveClub(null, Role.STUDENT));

        clubRepository.delete(club);
    }
}
