package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.QQueryClubVO;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.club.domain.QClub.club;

@RequiredArgsConstructor
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ClubVO> findAllByIsOpenIsTrue() {
        return jpaQueryFactory.select(
            new QQueryClubVO(
                club.id,
                club.clubName,
                club.clubImage,
                club.introduction
            )
        )
        .from(club)
        .leftJoin(club.clubMajors)
        .where(club.isOpen.isTrue())
        .fetch()
        .stream()
        .map(ClubVO.class::cast)
        .toList();
    }
}
