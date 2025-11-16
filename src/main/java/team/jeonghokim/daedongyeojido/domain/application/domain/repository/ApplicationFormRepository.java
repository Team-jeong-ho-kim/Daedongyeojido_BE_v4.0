package team.jeonghokim.daedongyeojido.domain.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;

import java.util.List;

public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {

    List<ApplicationForm> findAllByClubId(Long clubId);
}
