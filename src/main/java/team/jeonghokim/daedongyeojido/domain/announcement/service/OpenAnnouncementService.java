package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.exception.AnnouncementAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.announcement.facade.AnnouncementFacade;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class OpenAnnouncementService {

    private final UserFacade userFacade;

    private final AnnouncementFacade announcementFacade;

    @Transactional
    public void execute(Long announcementId) {
        User user = userFacade.getCurrentUser();

        Announcement announcement = announcementFacade.getAnnouncementById(announcementId);

        if (user.getClub() == null || !user.getClub().equals(announcement.getClub())) {
            throw AnnouncementAccessDeniedException.EXCEPTION;
        }

        announcement.open();
    }
}
