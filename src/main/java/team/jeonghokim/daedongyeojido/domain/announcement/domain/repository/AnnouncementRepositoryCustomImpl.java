package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.AnnouncementResponse;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.ClubAnnouncementResponse;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.QAnnouncementResponse;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.QClubAnnouncementResponse;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.announcement.domain.QAnnouncement.announcement;

@RequiredArgsConstructor
public class AnnouncementRepositoryCustomImpl implements AnnouncementRepositoryCustom {

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

    @Override
    public List<ClubAnnouncementResponse> findAllClubAnnouncementsByClubId(Long clubId) {
        return jpaQueryFactory
                .select(new QClubAnnouncementResponse(
                        announcement.id,
                        announcement.title,
                        announcement.deadline
                ))
                .from(announcement)
                .where(announcement.club.id.eq(clubId))
                .fetch();

    }
}
