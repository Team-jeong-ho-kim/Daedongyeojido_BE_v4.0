package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.exception.AnnouncementNotFoundException;

@Service
@RequiredArgsConstructor
public class DeleteAnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Transactional
    public void execute(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> AnnouncementNotFoundException.EXCEPTION);

        announcementRepository.delete(announcement);
    }
}
