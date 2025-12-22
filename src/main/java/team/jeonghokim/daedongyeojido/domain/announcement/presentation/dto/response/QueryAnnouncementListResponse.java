package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryAnnouncementListResponse(List<AnnouncementResponse> announcements) {

    public static QueryAnnouncementListResponse from(List<AnnouncementResponse> announcements) {
        return QueryAnnouncementListResponse.builder()
                .announcements(announcements)
                .build();
    }
}
