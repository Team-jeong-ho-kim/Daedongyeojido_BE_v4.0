package team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;

import java.util.List;

@Getter
public class QueryClubVO extends ClubVO {

    @QueryProjection
    public QueryClubVO(Long clubId, String clubName, String clubImage, String introduction, List<ClubMajor> clubMajors) {
        super(clubId, clubName, clubImage, introduction, clubMajors.stream().map(ClubMajor::getMajor).toList());
    }
}
