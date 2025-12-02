package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmRepositoryCustom {

    Optional<Alarm> findByClubAndAlarmType(Club club, AlarmType alarmType);
}
