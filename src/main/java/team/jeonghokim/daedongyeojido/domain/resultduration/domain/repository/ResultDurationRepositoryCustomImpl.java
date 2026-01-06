package team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResultDurationRepositoryCustomImpl implements ResultDurationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
