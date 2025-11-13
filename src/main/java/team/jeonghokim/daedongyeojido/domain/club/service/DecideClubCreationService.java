package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.AlarmRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.exception.AlarmNotFoundException;
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
    private final AlarmRepository alarmRepository;

    @Transactional
    public void execute(Long clubId, DecideClubCreationRequest request) {
        Club club = clubFacade.getClubById(clubId);
        User user = userRepository.findById(club.getClubApplicant().getId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Alarm alarm = alarmRepository.findByClubAndAlarmType(club, AlarmType.CREATE_CLUB)
                .orElseThrow(() -> AlarmNotFoundException.EXCEPTION);

        if (request.isApproved()) {
            club.clubOpen();
            user.approvedClub(club);
            alarmRepository.delete(alarm);
        } else {
            alarmRepository.delete(alarm);
        }
    }
}
