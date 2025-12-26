package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.ClubAlarm;

public interface ClubAlarmRepository extends JpaRepository<ClubAlarm, Long>, AlarmRepositoryCustom {
}
