package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.QClub;
import team.jeonghokim.daedongyeojido.domain.club.domain.QClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.QClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.QQueryClubVO;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.QueryClubVO;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.club.domain.QClub.club;

@RequiredArgsConstructor
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QClub club = QClub.club;
    private final QClubMajor clubMajor = QClubMajor.clubMajor;
    private final QClubLink  clubLink = QClubLink.clubLink;

    @Override
    public List<ClubVO> findAllByIsOpenIsTrue() {
        return jpaQueryFactory
                .from(club)
                .leftJoin(club.clubMajors, clubMajor)
                .leftJoin(club.clubLinks, clubLink)
                .where(club.isOpen.isTrue())
                .transform(
                        GroupBy.groupBy(club.id).list(
                                Projections.constructor(
                                        ClubVO.class,
                                        club.id,
                                        club.clubName,
                                        club.clubImage,
                                        club.introduction,
                                        GroupBy.list(clubMajor.major),
                                        GroupBy.list(clubLink.link)
                                )
                        )
                );
    }
}
