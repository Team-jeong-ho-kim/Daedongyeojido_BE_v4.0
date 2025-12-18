package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
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

    public static ClubDetailDto from(Club club) {
        return new ClubDetailDto(
                club.getClubName(),
                club.getIntroduction(),
                club.getOneLiner(),
                club.getClubImage(),
                club.getClubMajors().stream().map(ClubMajor::getMajor).toList(),
                club.getClubLinks().stream().map(ClubLink::getLink).toList()
        );
    }
}
