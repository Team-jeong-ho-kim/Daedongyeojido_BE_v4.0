package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import java.util.List;

public record QueryAnnouncementListResponse(List<AnnouncementResponse> announcements) {
}
