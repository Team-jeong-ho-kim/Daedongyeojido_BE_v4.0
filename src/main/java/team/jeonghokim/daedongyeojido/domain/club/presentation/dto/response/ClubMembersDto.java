package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record ClubMembersDto(
        String userName,
        List<Major> majors,
        String introduction
) {

    @QueryProjection
    public ClubMembersDto(String userName, List<Major> majors, String introduction) {
        this.userName = userName;
        this.majors = majors;
        this.introduction = introduction;
    }
}
