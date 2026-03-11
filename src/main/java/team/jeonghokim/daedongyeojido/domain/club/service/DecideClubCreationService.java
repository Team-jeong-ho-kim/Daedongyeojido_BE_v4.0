package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.factory.AlarmEventFactory;

@Service
@RequiredArgsConstructor
public class DecideClubCreationService {

    private final UserRepository userRepository;
    private final ClubFacade clubFacade;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmEventFactory alarmEventFactory;
    private final ClubRepository clubRepository;

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
            club.clubRejected();

            rejectClub(club, user);
        }
    }

    private void acceptClub(Club club, User user) {

        eventPublisher.publishEvent(
                alarmEventFactory.createUserAlarmEvent(user, club, AlarmType.CLUB_CREATION_ACCEPTED)
        );
    }

    private void rejectClub(Club club, User user) {

        eventPublisher.publishEvent(
                alarmEventFactory.createUserAlarmEvent(user, club, AlarmType.CLUB_CREATION_REJECTED)
        );
    }
}
