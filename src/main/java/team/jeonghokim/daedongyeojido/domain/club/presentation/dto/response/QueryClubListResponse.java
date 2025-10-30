package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

@Getter
@AllArgsConstructor
public class QueryClubListResponse {

    private final List<ClubDto> clubs;

    @Getter
    @Builder
    public static final class ClubDto {
        private final Long clubId;
        private final String clubName;
        private final String clubImage;
        private final String introduction;
        private final List<Major> majors;

        public static ClubDto from(Club club) {
            return ClubDto.builder()
                    .clubId(club.getId())
                    .clubName(club.getClubName())
                    .clubImage(club.getClubImage())
                    .introduction(club.getIntroduction())
                    .majors(
                            club.getMajors().stream()
                                    .map(ClubMajor::getMajor)
                                    .toList()
                    )
                    .build();
        }
    }
}
