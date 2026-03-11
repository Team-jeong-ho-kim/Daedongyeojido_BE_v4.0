package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.enums.ClubStatus;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotOpenException;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.DecideClubDissolveRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.factory.AlarmEventFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DecideClubDissolveService {

    private final ClubFacade clubFacade;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmEventFactory alarmEventFactory;

    @Transactional
    public void execute(Long clubId, DecideClubDissolveRequest request) {

        Club club = clubFacade.getClubById(clubId);

        User user = userRepository.findById(club.getClubApplicant().getId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!club.getClubStatus().equals(ClubStatus.OPEN)) {
            throw ClubNotOpenException.EXCEPTION;
        }

        if (request.isDecision()) {

            List<User> clubMembers = userRepository.findAllByClub(club);

            clubMembers.forEach(User::leaveClub);

            acceptDissolution(club, user);

            clubRepository.delete(club);
        } else {

            rejectDissolution(club, user);
        }
    }

    private void acceptDissolution(Club club, User user) {

        eventPublisher.publishEvent(
                alarmEventFactory.createUserAlarmEvent(user, club, AlarmType.CLUB_DISSOLUTION_ACCEPTED)
        );
    }

    private void rejectDissolution(Club club, User user) {

        eventPublisher.publishEvent(
                alarmEventFactory.createUserAlarmEvent(user, club, AlarmType.CLUB_DISSOLUTION_REJECTED)
        );
    }
}
