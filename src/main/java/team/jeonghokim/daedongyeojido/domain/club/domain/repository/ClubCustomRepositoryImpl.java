package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubListResponse;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.club.domain.QClub.club;
import static team.jeonghokim.daedongyeojido.domain.club.domain.QClubMajor.clubMajor;

@RequiredArgsConstructor
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<QueryClubListResponse.ClubDto> findAllByIsOpenIsTrue() {
        List<Club> clubs = jpaQueryFactory
                .selectDistinct(club)
                .from(club)
                .leftJoin(club.clubMajors).fetchJoin()
                .where(club.isOpen.isTrue())
                .fetch();

        return clubs.stream()
                .map(QueryClubListResponse.ClubDto::from)
                .toList();
    }
}
