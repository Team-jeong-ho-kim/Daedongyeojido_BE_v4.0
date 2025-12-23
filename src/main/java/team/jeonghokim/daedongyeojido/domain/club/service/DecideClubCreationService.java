package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
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
        Club club = clubFacade.getClubById(clubId);
        User user = userRepository.findById(club.getClubApplicant().getId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (request.isOpen()) {
            club.clubOpen();
            user.approvedClub(club);
            acceptClub(club, user);
        } else {
            rejectClub(club, user);
        }
    }

    private void acceptClub(Club club, User user) {
        Alarm alarm = Alarm.builder()
                .title(AlarmType.CLUB_CREATION_ACCEPTED.getTitle())
                .content(AlarmType.CLUB_CREATION_ACCEPTED.format(club.getClubName()))
                .club(club)
                .receiver(user)
                .alarmType(AlarmType.CLUB_CREATION_ACCEPTED)
                .build();

        user.getAlarms().add(alarm);
    }

    private void rejectClub(Club club, User user) {
        Alarm alarm = Alarm.builder()
                .title(AlarmType.CLUB_CREATION_REJECTED.getTitle())
                .content(AlarmType.CLUB_CREATION_REJECTED.format(club.getClubName()))
                .club(club)
                .receiver(user)
                .alarmType(AlarmType.CLUB_CREATION_REJECTED)
                .build();

        user.getAlarms().add(alarm);
    }
}
