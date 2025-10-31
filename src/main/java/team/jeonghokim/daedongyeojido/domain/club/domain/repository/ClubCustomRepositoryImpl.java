package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.club.domain.QClub.club;
import static team.jeonghokim.daedongyeojido.domain.club.domain.QClubMajor.clubMajor;

@RequiredArgsConstructor
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Club> findAllByIsOpenIsTrue() {
        return jpaQueryFactory
                .selectFrom(club)
                .leftJoin(club.majors, clubMajor).fetchJoin()
                .where(club.isOpen.isTrue())
                .distinct()
                .fetch();
    }
}
