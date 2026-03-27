package team.jeonghokim.daedongyeojido.domain.schedule.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    boolean existsByApplicantAndClub(User applicant, Club club);

    Optional<Schedule> findByApplicantIdAndClubId(Long applicantId, Long clubId);
}
