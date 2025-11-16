package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository;

import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.vo.AnnouncementVO;

import java.util.List;

public interface AnnouncementCustomRepository {

    List<AnnouncementVO> findAllAnnouncementVO();
}
