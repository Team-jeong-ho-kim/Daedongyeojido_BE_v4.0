package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.AdminAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.AlarmResponse;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.QueryAdminAlarmResponse;


import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAdminAlarmService {

    private final AdminAlarmRepository adminAlarmRepository;

    @Transactional(readOnly = true)
    public QueryAdminAlarmResponse execute() {

        List<AlarmResponse> alarms = adminAlarmRepository.findAll()
                .stream()
                .map(AlarmResponse::from)
                .toList();

        return QueryAdminAlarmResponse.from(alarms);
    }
}
