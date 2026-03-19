package team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response;

import java.util.List;

public record QueryClubCreationApplicationListResponse(
        List<ClubCreationApplicationSummaryResponse> applications
) {
    public static QueryClubCreationApplicationListResponse from(List<ClubCreationApplicationSummaryResponse> applications) {
        return new QueryClubCreationApplicationListResponse(applications);
    }
}
