package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryClubAnnouncementResponse(List<ClubAnnouncementResponse> clubAnnouncements) {

    public static QueryClubAnnouncementResponse from(List<ClubAnnouncementResponse> clubAnnouncements) {
        return QueryClubAnnouncementResponse.builder()
                .clubAnnouncements(clubAnnouncements)
                .build();
    }
}
