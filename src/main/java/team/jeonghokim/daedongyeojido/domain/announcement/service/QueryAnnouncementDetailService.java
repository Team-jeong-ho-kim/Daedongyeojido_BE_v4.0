package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.facade.AnnouncementFacade;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.QueryAnnouncementDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryAnnouncementDetailService {

    private final AnnouncementFacade announcementFacade;

    @Transactional(readOnly = true)
    public QueryAnnouncementDetailResponse execute(Long announcementId) {
        Announcement announcement = announcementFacade.getAnnouncementById(announcementId);
        return QueryAnnouncementDetailResponse.from(announcement);
    }
}
