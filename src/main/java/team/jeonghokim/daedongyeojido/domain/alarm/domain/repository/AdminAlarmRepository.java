package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.AdminAlarm;

public interface AdminAlarmRepository extends JpaRepository<AdminAlarm, Long> {
}
