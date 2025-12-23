package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.AlarmResponse;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.QueryUserAlarmResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserAlarmService {
    private final UserFacade userFacade;
    private final UserAlarmRepository userAlarmRepository;

    @Transactional(readOnly = true)
    public QueryUserAlarmResponse execute() {
        User user = userFacade.getCurrentUser();

        List<AlarmResponse> alarmResponses = userAlarmRepository.findAllByUserId(user.getId());

        return new QueryUserAlarmResponse(alarmResponses);
    }
}
