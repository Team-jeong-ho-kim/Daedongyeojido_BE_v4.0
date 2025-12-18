package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.AnnouncementResponse;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.QAnnouncementResponse;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.announcement.domain.QAnnouncement.announcement;

@RequiredArgsConstructor
public class AnnouncementCustomRepositoryImpl implements AnnouncementRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AnnouncementResponse> findAllAnnouncements() {
        return jpaQueryFactory
                .select(new QAnnouncementResponse(
                        announcement.id,
                        announcement.title,
                        announcement.club.clubName,
                        announcement.deadline,
                        announcement.club.clubImage
                ))
                .from(announcement)
                .fetch();
    }
}
