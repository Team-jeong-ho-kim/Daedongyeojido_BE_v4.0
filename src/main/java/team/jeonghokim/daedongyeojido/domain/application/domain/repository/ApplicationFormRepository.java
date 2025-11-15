package team.jeonghokim.daedongyeojido.domain.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;

import java.util.Optional;

public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {
    Optional<ApplicationForm> findByClubId(Long clubId);
}
