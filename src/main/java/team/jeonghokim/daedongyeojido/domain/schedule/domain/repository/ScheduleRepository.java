package team.jeonghokim.daedongyeojido.domain.schedule.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
