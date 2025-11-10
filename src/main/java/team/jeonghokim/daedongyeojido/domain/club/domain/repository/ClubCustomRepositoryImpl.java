package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.QClub;
import team.jeonghokim.daedongyeojido.domain.club.domain.QClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;

import java.util.List;

@RequiredArgsConstructor
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QClub club = QClub.club;
    private final QClubMajor clubMajor = QClubMajor.clubMajor;

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
