package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.exception.AnnouncementNotFoundException;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.request.UpdateAnnouncementRequest;

@Service
@RequiredArgsConstructor
public class UpdateAnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Transactional
    public void execute(Long announcementId, UpdateAnnouncementRequest request) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> AnnouncementNotFoundException.EXCEPTION);

        announcement.updateAnnouncement(request);
    }
}
