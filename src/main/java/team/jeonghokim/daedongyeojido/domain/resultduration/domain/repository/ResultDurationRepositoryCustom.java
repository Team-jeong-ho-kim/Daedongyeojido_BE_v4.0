package team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository;

import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;

import java.util.Optional;

public interface ResultDurationRepositoryCustom {

    Optional<ResultDuration> findPendingResultDuration();

    Optional<ResultDuration> findPendingResultDurationForUpdate();
}
