package team.jeonghokim.daedongyeojido.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserAlarm;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {
}
