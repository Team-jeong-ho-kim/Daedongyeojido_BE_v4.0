package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.UserAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmCategory;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.UserAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.exception.AlarmAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.alarm.exception.AlarmNotFoundException;
import team.jeonghokim.daedongyeojido.domain.alarm.exception.CannotDeleteAlarmException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class DeleteUserAlarmService {

    private final UserAlarmRepository userAlarmRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long alarmId) {

        User user = userFacade.getCurrentUser();

        UserAlarm alarm = userAlarmRepository.findById(alarmId)
                .orElseThrow(() -> AlarmNotFoundException.EXCEPTION);

        if (!user.getId().equals(alarm.getReceiver().getId())) {
            throw AlarmAccessDeniedException.EXCEPTION;
        }

        if (!alarm.getAlarmCategory().equals(AlarmCategory.COMMON) && !alarm.isExecuted()) {
            throw CannotDeleteAlarmException.EXCEPTION;
        }

        userAlarmRepository.delete(alarm);
    }
}
