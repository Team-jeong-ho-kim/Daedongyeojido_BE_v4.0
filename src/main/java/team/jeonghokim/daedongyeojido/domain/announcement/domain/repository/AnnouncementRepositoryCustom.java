package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository;

import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.AnnouncementResponse;

import java.util.List;

public interface AnnouncementRepositoryCustom {

    List<AnnouncementResponse> findAllAnnouncements();
}
