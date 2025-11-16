package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class QueryAnnouncementVO extends AnnouncementVO {

    @QueryProjection
    public QueryAnnouncementVO(Long announcementId, String title, String clubName, LocalDate deadline, String clubImage) {
        super(announcementId, title, clubName, deadline, clubImage);
    }
}
