package team.jeonghokim.daedongyeojido.domain.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormListResponse;

import java.util.List;
import java.util.Optional;

public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {
    Optional<ApplicationForm> findByClubId(Long clubId);

    List<ApplicationFormListResponse> findAllApplicationFormListResponse();
}
