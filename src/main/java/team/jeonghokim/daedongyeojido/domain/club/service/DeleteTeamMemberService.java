package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubMisMatchException;
import team.jeonghokim.daedongyeojido.domain.club.exception.UserNotInClubException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class DeleteTeamMemberService {

    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long userId) {
        User clubLeader = userFacade.getCurrentUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (user.getClub() == null) {
            throw UserNotInClubException.EXCEPTION;
        }

        if (!(clubLeader.getClub().equals(user.getClub()))) {
            throw ClubMisMatchException.EXCEPTION;
        }

        user.leaveClub();
        deleteClubMember(clubLeader.getClub(), user);
    }

    private void deleteClubMember(Club club, User user) {
        Alarm alarm = Alarm.builder()
                .title(AlarmType.DELETE_CLUB_MEMBER.format(club.getClubName()))
                .content(AlarmType.DELETE_CLUB_MEMBER.format(club.getClubName()))
                .club(club)
                .receiver(user)
                .alarmType(AlarmType.DELETE_CLUB_MEMBER)
                .build();

        user.getAlarms().add(alarm);
    }
}
