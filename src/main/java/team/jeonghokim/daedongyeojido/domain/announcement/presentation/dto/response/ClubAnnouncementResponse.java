package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.enums.Status;

import java.time.LocalDate;

public record ClubAnnouncementResponse(Long announcementId, String title, LocalDate deadline, Status status) {

    @QueryProjection
    public ClubAnnouncementResponse(Long announcementId, String title, LocalDate deadline, Status status) {
        this.announcementId = announcementId;
        this.title = title;
        this.deadline = deadline;
        this.status = status;
    }
}
