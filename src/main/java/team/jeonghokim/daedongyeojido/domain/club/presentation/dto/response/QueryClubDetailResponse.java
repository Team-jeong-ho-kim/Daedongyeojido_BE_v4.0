package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

import java.util.List;

public record QueryClubDetailResponse(
        ClubDetailDto club,
        List<ClubMembersDto> clubMembers
) {

    public static QueryClubDetailResponse of(Club club, List<User> clubMembers) {
        return new QueryClubDetailResponse(
                ClubDetailDto.from(club),
                clubMembers.stream()
                        .map(ClubMembersDto::from)
                        .toList()
        );
    }
}
