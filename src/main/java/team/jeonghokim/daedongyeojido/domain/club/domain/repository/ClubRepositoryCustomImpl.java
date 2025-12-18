package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.ClubDetailDto;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.ClubMembersDto;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QClubDetailDto;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QClubMembersDto;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static team.jeonghokim.daedongyeojido.domain.club.domain.QClub.club;
import static team.jeonghokim.daedongyeojido.domain.club.domain.QClubLink.clubLink;
import static team.jeonghokim.daedongyeojido.domain.club.domain.QClubMajor.clubMajor;
import static team.jeonghokim.daedongyeojido.domain.user.domain.QUser.user;
import static team.jeonghokim.daedongyeojido.domain.user.domain.QUserMajor.userMajor;

@RequiredArgsConstructor
public class ClubRepositoryCustomImpl implements ClubRepositoryCustom {

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

    @Override
    public List<ClubMembersDto> findClubMembersById(Long clubId) {
        return jpaQueryFactory
                .from(user)
                .leftJoin(user.majors, userMajor)
                .where(user.club.id.eq(clubId))
                .transform(groupBy(user.id).list(
                        new QClubMembersDto(
                                user.userName,
                                list(userMajor.major),
                                user.introduction
                        )
                ));
    }

    @Override
    public Optional<ClubDetailDto> findClubDetailById(Long clubId) {
        return jpaQueryFactory
                .from(club)
                .leftJoin(club.clubMajors, clubMajor)
                .leftJoin(club.clubLinks, clubLink)
                .where(club.id.eq(clubId))
                .transform(groupBy(club.id).list(
                        new QClubDetailDto(
                                club.clubName,
                                club.introduction,
                                club.oneLiner,
                                club.clubImage,
                                list(clubMajor.major),
                                list(clubLink.link)
                        )
                ))
                .stream()
                .findFirst();
    }
}
