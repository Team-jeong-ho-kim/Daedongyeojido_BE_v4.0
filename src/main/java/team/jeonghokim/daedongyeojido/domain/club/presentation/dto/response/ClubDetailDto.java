package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record ClubDetailDto(
        String clubName,
        String introduction,
        String oneLiner,
        String clubImage,
        List<Major> majors,
        List<String> links
) {

    @QueryProjection
    public ClubDetailDto(String clubName, String introduction, String oneLiner, String clubImage, List<Major> majors, List<String> links) {
        this.clubName = clubName;
        this.introduction = introduction;
        this.oneLiner = oneLiner;
        this.clubImage = clubImage;
        this.majors = majors;
        this.links = links;
    }
}
