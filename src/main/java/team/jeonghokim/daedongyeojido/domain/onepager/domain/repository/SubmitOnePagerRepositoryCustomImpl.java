package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.onepager.domain.QOnePager.onePager;
import static team.jeonghokim.daedongyeojido.domain.onepager.domain.QSubmitOnePager.submitOnePager;

@RequiredArgsConstructor
public class SubmitOnePagerRepositoryCustomImpl implements SubmitOnePagerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SubmitOnePager> findByClub(Club club) {
        return jpaQueryFactory
                .selectFrom(submitOnePager)
                .join(submitOnePager.formOnePager, onePager).fetchJoin()
                .where(submitOnePager.club.eq(club))
                .fetch();
    }
}
