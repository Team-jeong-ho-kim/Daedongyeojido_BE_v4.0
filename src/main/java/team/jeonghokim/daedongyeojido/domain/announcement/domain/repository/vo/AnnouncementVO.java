package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AnnouncementVO {

    private final Long announcementId;
    private final String title;
    private final String clubName;
    private final LocalDate deadline;
    private final String clubImage;
}
