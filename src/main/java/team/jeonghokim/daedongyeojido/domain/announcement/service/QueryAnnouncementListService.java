package team.jeonghokim.daedongyeojido.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.vo.AnnouncementVO;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.QueryAnnouncementListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAnnouncementListService {

    private final AnnouncementRepository announcementRepository;

    @Transactional(readOnly = true)
    public QueryAnnouncementListResponse execute() {
        List<AnnouncementVO> announcements = announcementRepository.findAllAnnouncementVO();
        return new QueryAnnouncementListResponse(announcements);
    }
}
