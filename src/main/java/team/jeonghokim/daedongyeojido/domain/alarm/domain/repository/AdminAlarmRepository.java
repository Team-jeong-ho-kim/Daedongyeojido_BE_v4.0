package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.AdminAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.AlarmResponse;

import java.util.List;

public interface AdminAlarmRepository extends JpaRepository<AdminAlarm, Long> {

    List<AlarmResponse> findAllAlarms();
}
