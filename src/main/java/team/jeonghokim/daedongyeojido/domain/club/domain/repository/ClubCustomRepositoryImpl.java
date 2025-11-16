package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.club.domain.QClub.club;
import static team.jeonghokim.daedongyeojido.domain.club.domain.QClubMajor.clubMajor;

@RequiredArgsConstructor
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ClubVO> findAllByIsOpenIsTrue() {
        return jpaQueryFactory
                .from(club)
                .leftJoin(club.clubMajors, clubMajor)
                .where(club.isOpen.isTrue())
                .transform(
                        GroupBy.groupBy(club.id).list(
                                Projections.constructor(
                                        ClubVO.class,
                                        club.id,
                                        club.clubName,
                                        club.clubImage,
                                        club.introduction,
                                        GroupBy.list(clubMajor.major)
                                )
                        )
                );
    }
}
