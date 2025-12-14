package team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;

import java.util.Optional;

public interface ResultDurationRepository extends JpaRepository<ResultDuration, Long> {

    Optional<ResultDuration> findTopByOrderByIdDesc();
}
