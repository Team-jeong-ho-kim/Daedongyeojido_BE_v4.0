package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record ClubAnnouncementResponse(Long announcementId, String title, LocalDate deadline) {

    @QueryProjection
    public ClubAnnouncementResponse(Long announcementId, String title, LocalDate deadline) {
        this.announcementId = announcementId;
        this.title = title;
        this.deadline = deadline;
    }
}
