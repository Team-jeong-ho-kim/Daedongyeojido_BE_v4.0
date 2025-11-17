package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.facade.AnnouncementFacade;

@Service
@RequiredArgsConstructor
public class DeleteAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementFacade announcementFacade;

    @Transactional
    public void execute(Long announcementId) {
        Announcement announcement = announcementFacade.getAnnouncementById(announcementId);
        announcementRepository.delete(announcement);
    }
}
