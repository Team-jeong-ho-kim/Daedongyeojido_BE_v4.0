package team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.QResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.enums.Status;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResultDurationRepositoryCustomImpl implements ResultDurationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QResultDuration resultDuration = QResultDuration.resultDuration;

    @Override
    public Optional<ResultDuration> findSmsStatusPendingResultDuration() {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(resultDuration)
                        .where(resultDuration.smsStatus.eq(Status.PENDING))
                        .fetchOne()
        );
    }

    @Override
    public Optional<ResultDuration> findSmsStatusPendingResultDurationForUpdate() {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(resultDuration)
                        .where(resultDuration.smsStatus.eq(Status.PENDING))
                        .orderBy(resultDuration.id.desc())
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .fetchOne()
        );
    }
}
