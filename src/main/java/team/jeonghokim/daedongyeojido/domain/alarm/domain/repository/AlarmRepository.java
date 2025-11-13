package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    void deleteAllByClub(Club club);
}
