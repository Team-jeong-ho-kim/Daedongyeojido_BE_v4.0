package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.AnnouncementMajor;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.request.AnnouncementRequest;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(AnnouncementRequest request) {
        User user = userFacade.getCurrentUser();

        List<AnnouncementMajor> announcementMajors = createAnnouncementMajor(request);
        Announcement announcement = createAnnouncement(request, user.getClub(), announcementMajors);

        announcementRepository.save(announcement);
    }

    private Announcement createAnnouncement(
            AnnouncementRequest request,
            Club club,
            List<AnnouncementMajor> announcementMajors
    ) {
        return Announcement.builder()
                .title(request.title())
                .deadline(request.deadline())
                .introduction(request.introduction())
                .talentDescription(request.talentDescription())
                .assignment(request.assignment())
                .phoneNumber(request.phoneNumber())
                .club(club)
                .announcementMajors(announcementMajors)
                .build();
    }

    private List<AnnouncementMajor> createAnnouncementMajor(AnnouncementRequest request) {
        return request.major().stream().map(major ->
                        AnnouncementMajor.builder()
                                .major(major)
                                .build())
                .collect(Collectors.toList());
    }
}
