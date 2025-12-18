package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository;

import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.AnnouncementResponse;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.ClubAnnouncementResponse;

import java.util.List;

public interface AnnouncementRepositoryCustom {

    List<AnnouncementResponse> findAllAnnouncements();

    List<ClubAnnouncementResponse> findAllClubAnnouncementsByClubId(Long clubId);
}
