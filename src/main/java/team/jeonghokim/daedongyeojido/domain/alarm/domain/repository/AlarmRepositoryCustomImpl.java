package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlarmRepositoryCustomImpl implements AlarmRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    

}
