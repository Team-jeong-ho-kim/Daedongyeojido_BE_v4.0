package team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.QResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.enums.Status;

import java.util.Optional;

@RequiredArgsConstructor
public class ResultDurationRepositoryCustomImpl implements ResultDurationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QResultDuration resultDuration = QResultDuration.resultDuration;

    @Override
    public Optional<ResultDuration> findPendingResultDuration() {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(resultDuration)
                        .where(resultDuration.status.eq(Status.PENDING))
                        .fetchOne()
        );
    }
}
