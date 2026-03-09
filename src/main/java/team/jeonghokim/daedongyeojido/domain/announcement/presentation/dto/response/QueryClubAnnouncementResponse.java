package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import java.util.List;

public record QueryClubAnnouncementResponse(List<ClubAnnouncementResponse> clubAnnouncements) {

    public static QueryClubAnnouncementResponse from(List<ClubAnnouncementResponse> clubAnnouncements) {
        return new QueryClubAnnouncementResponse(clubAnnouncements);
    }
}
