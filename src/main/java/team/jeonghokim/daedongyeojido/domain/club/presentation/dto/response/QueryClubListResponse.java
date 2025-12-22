package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;

import java.util.List;

@Builder
public record QueryClubListResponse(List<ClubVO> clubs) {
    public static QueryClubListResponse from(List<ClubVO> clubs) {
        return QueryClubListResponse.builder()
                .clubs(clubs)
                .build();
    }
}
