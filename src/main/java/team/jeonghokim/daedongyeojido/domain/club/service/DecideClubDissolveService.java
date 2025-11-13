package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.AlarmRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.exception.AlarmNotFoundException;
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
    private final AlarmRepository alarmRepository;

    @Transactional
    public void execute(Long clubId, DecideClubDissolveRequest request) {
        Club club = clubFacade.getClubById(clubId);

        if (!club.getIsOpen()) {
            throw ClubNotOpenException.EXCEPTION;
        }

        Alarm alarm = alarmRepository.findByClubAndAlarmType(club, AlarmType.DISSOLVE_CLUB)
                .orElseThrow(() -> AlarmNotFoundException.EXCEPTION);

        if (request.isDecision()) {
            List<User> clubMembers = userRepository.findAllByClub(club);
            clubMembers.forEach(member -> member.leaveClub(null, Role.STUDENT));
            clubRepository.delete(club);
        }

        alarmRepository.delete(alarm);
    }
}
