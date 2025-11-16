package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.vo.AnnouncementVO;

import java.util.List;

public record QueryAnnouncementListResponse(List<AnnouncementVO> announcements) {
}
