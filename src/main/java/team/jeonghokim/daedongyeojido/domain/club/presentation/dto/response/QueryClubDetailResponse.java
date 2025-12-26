package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryClubDetailResponse(
        ClubDetailDto club,
        List<ClubMembersDto> clubMembers
) {
    public static QueryClubDetailResponse of(ClubDetailDto clubDetailDto, List<ClubMembersDto> clubMembers) {
        return QueryClubDetailResponse.builder()
                .club(clubDetailDto)
                .clubMembers(clubMembers)
                .build();
    }
}
