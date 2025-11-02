package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record QueryClubDetailResponse(
        String clubName,
        String introduction,
        String clubImage,
        List<Major> majors,
        List<String> links
) {

    public static QueryClubDetailResponse of(Club club) {
        return new QueryClubDetailResponse(
                club.getClubName(),
                club.getIntroduction(),
                club.getClubImage(),
                club.getMajors().stream().map(ClubMajor::getMajor).toList(),
                club.getLinks().stream().map(ClubLink::getLink).toList()
        );
    }
}
