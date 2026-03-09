package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.UserAlarm;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long>, AlarmRepositoryCustom {
}
