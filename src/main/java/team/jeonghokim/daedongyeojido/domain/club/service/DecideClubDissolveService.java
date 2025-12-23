package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotOpenException;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.DecideClubDissolveRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DecideClubDissolveService {

    private final ClubFacade clubFacade;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Transactional
    public void execute(Long clubId, DecideClubDissolveRequest request) {
        Club club = clubFacade.getClubById(clubId);
        User user = userRepository.findById(club.getClubApplicant().getId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!club.getIsOpen()) {
            throw ClubNotOpenException.EXCEPTION;
        }

        if (request.isDecision()) {
            List<User> clubMembers = userRepository.findAllByClub(club);
            clubMembers.forEach(User::leaveClub);
            clubRepository.delete(club);
            acceptDissolution(club, user);
        } else {
            rejectDissolution(club, user);
        }
    }

    private void acceptDissolution(Club club, User user) {
        Alarm alarm = Alarm.builder()
                .title(AlarmType.CLUB_DISSOLUTION_ACCEPTED.getTitle())
                .content(AlarmType.CLUB_DISSOLUTION_ACCEPTED.format(club.getClubName()))
                .club(club)
                .receiver(user)
                .alarmType(AlarmType.CLUB_DISSOLUTION_ACCEPTED)
                .build();

        user.getAlarms().add(alarm);
    }

    private void rejectDissolution(Club club, User user) {
        Alarm alarm = Alarm.builder()
                .title(AlarmType.CLUB_DISSOLUTION_REJECTED.getTitle())
                .content(AlarmType.CLUB_DISSOLUTION_REJECTED.format(club.getClubName()))
                .club(club)
                .receiver(user)
                .alarmType(AlarmType.CLUB_DISSOLUTION_REJECTED)
                .build();

        user.getAlarms().add(alarm);
    }
}
