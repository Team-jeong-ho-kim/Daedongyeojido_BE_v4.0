package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record QueryClubDetailResponse(ClubDto club) {

    public record ClubDto(
            String clubName,
            String introduction,
            String clubImage,
            List<Major> majors,
            List<String> links
    ) {
        public static QueryClubDetailResponse.ClubDto from(Club club) {
            return new QueryClubDetailResponse.ClubDto(
                    club.getClubName(),
                    club.getClubImage(),
                    club.getIntroduction(),
                    club.getMajors().stream().map(ClubMajor::getMajor).toList(),
                    club.getLinks().stream().map(ClubLink::getLink).toList()
            );
        }
    }
}
