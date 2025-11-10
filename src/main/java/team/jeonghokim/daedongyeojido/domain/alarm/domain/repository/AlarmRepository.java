package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
