package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import java.util.List;

public record QueryClubDetailResponse(
        ClubDetailDto club,
        List<ClubMembersDto> clubMembers
) {

    public static QueryClubDetailResponse of(ClubDetailDto clubDetailDto, List<ClubMembersDto> clubMembers) {
        return new QueryClubDetailResponse(clubDetailDto, clubMembers);
    }
}
