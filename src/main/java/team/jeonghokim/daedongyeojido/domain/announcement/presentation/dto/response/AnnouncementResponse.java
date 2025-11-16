package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import java.time.LocalDate;

public record AnnouncementResponse(Long announcementId, String title, String clubName, LocalDate deadline, String clubImage) {

}
