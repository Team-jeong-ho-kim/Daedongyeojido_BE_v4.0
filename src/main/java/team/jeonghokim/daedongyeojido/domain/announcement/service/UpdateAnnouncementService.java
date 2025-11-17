package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.exception.AnnouncementAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.announcement.exception.AnnouncementNotFoundException;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.request.UpdateAnnouncementRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class UpdateAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long announcementId, UpdateAnnouncementRequest request) {
        User currentUser = userFacade.getCurrentUser();
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> AnnouncementNotFoundException.EXCEPTION);

        if (!announcement.getClub().equals(currentUser.getClub())) {
            throw AnnouncementAccessDeniedException.EXCEPTION;
        }

        announcement.updateAnnouncement(request);
    }
}
