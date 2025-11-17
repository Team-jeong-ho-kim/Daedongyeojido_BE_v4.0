package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record AnnouncementResponse(Long announcementId, String title, String clubName, LocalDate deadline, String clubImage) {

    @QueryProjection
    public AnnouncementResponse(Long announcementId, String title, String clubName, LocalDate deadline, String clubImage) {
        this.announcementId = announcementId;
        this.title = title;
        this.clubName = clubName;
        this.deadline = deadline;
        this.clubImage = clubImage;
    }
}
