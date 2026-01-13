package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.domain.Admin;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.AdminAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.AlarmResponse;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.QueryAdminAlarmResponse;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAdminAlarmService {
    private final UserFacade userFacade;
    private final AdminAlarmRepository adminAlarmRepository;

    @Transactional(readOnly = true)
    public QueryAdminAlarmResponse execute() {

        Admin admin = userFacade.getCurrentAdmin();

        List<AlarmResponse> alarms = adminAlarmRepository.findAllAlarms();

        return QueryAdminAlarmResponse.from(alarms);
    }
}
