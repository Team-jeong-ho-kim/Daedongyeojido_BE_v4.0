package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.ClubAnnouncementResponse;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.QueryClubAnnouncementResponse;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryClubAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final ClubFacade clubFacade;

    @Transactional(readOnly = true)
    public QueryClubAnnouncementResponse execute(Long clubId) {

        Club club = clubFacade.getClubById(clubId);

        List<ClubAnnouncementResponse> clubAnnouncementResponses = announcementRepository.findAllClubAnnouncementsByClubId(club.getId());

        return new QueryClubAnnouncementResponse(clubAnnouncementResponses);
    }
}
