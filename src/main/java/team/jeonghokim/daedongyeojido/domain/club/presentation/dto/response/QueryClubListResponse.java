package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;

import java.util.List;

public record QueryClubListResponse(List<ClubVO> clubs) {

    public static QueryClubListResponse from(List<ClubVO> clubs) {
        return new QueryClubListResponse(clubs);
    }
}
