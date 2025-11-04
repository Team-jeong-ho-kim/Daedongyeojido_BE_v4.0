package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record QueryClubListResponse(List<ClubDto> clubs) {

    public record ClubDto(
            Long clubId,
            String clubName,
            String clubImage,
            String introduction,
            List<Major> majors
    ) {
        public static ClubDto from(Club club) {
            return new ClubDto(
                    club.getId(),
                    club.getClubName(),
                    club.getClubImage(),
                    club.getIntroduction(),
                    club.getClubMajors().stream().map(ClubMajor::getMajor).toList()
            );
        }
    }
}
