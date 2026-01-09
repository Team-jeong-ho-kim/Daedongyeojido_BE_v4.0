package team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.enums.Status;
import team.jeonghokim.daedongyeojido.domain.resultduration.presentation.dto.response.ResultDurationResponse;

import java.util.Optional;

public interface ResultDurationRepository extends JpaRepository<ResultDuration, Long>, ResultDurationRepositoryCustom {

    Optional<ResultDuration> findTopByOrderByIdDesc();
    
    Boolean existsByStatus(Status status);
}
