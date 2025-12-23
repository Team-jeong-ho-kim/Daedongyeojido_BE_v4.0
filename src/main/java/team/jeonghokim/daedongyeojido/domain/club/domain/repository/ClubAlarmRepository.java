package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.AlarmRepositoryCustom;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubAlarm;

public interface ClubAlarmRepository extends JpaRepository<ClubAlarm, Long>, AlarmRepositoryCustom {
}
