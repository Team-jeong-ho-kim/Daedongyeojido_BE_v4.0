package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import java.util.List;

public record QueryClubDetailResponse(
        ClubDetailDto club,
        List<ClubMembersDto> clubMembers
) {
}
