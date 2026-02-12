package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.ClubAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.ClubAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.exception.AlarmAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.alarm.exception.AlarmNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.exception.UserNotInClubException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class DeleteClubAlarmService {

    private final ClubAlarmRepository clubAlarmRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long alarmId) {
        User user = userFacade.getCurrentUser();

        if (user.getClub() == null) {
            throw UserNotInClubException.EXCEPTION;
        }

        ClubAlarm clubAlarm = clubAlarmRepository.findById(alarmId)
                .orElseThrow(() -> AlarmNotFoundException.EXCEPTION);

        if (!clubAlarm.getClub().getId().equals(user.getClub().getId())) {
            throw AlarmAccessDeniedException.EXCEPTION;
        }

        clubAlarmRepository.delete(clubAlarm);
    }
}
