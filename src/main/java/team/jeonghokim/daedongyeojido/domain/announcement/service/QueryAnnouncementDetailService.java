package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.exception.AnnouncementNotFoundException;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.QueryAnnouncementDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryAnnouncementDetailService {

    private final AnnouncementRepository announcementRepository;

    @Transactional(readOnly = true)
    public QueryAnnouncementDetailResponse execute(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> AnnouncementNotFoundException.EXCEPTION);

        return QueryAnnouncementDetailResponse.from(announcement);
    }
}
