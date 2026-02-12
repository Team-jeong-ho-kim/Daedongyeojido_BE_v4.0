package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record ClubMembersDto(
        Long userId,
        String userName,
        List<Major> majors,
        String introduction,
        String profileImage
) {

    @QueryProjection
    public ClubMembersDto(Long userId, String userName, List<Major> majors, String introduction, String profileImage) {
        this.userId = userId;
        this.userName = userName;
        this.majors = majors;
        this.introduction = introduction;
        this.profileImage = profileImage;
    }
}
