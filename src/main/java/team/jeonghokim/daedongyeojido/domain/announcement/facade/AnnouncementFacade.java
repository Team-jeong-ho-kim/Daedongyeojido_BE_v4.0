package team.jeonghokim.daedongyeojido.domain.announcement.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.exception.AnnouncementNotFoundException;

@Component
@RequiredArgsConstructor
public class AnnouncementFacade {

    private final AnnouncementRepository announcementRepository;

    public Announcement getAnnouncementById(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .orElseThrow(() -> AnnouncementNotFoundException.EXCEPTION);
    }
}
