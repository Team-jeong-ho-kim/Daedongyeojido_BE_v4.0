package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.vo.AnnouncementVO;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.vo.QQueryAnnouncementVO;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.announcement.domain.QAnnouncement.announcement;

@RequiredArgsConstructor
public class AnnouncementCustomRepositoryImpl implements AnnouncementCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AnnouncementVO> findAllAnnouncementVO() {
        return jpaQueryFactory
                .select(
                        new QQueryAnnouncementVO(
                                announcement.id,
                                announcement.title,
                                announcement.club.clubName,
                                announcement.deadline,
                                announcement.club.clubImage
                        )
                )
                .from(announcement)
                .fetch()
                .stream()
                .map(AnnouncementVO.class::cast)
                .toList();
    }
}
