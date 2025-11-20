package team.jeonghokim.daedongyeojido.domain.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;

public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long>, ApplicationFormRepositoryCustom {

    ApplicationForm findByClub(Club club);
}
