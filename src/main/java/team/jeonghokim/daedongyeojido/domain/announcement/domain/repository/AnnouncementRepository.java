package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.AnnouncementResponse;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("""
        SELECT new team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.AnnouncementResponse(
            announcement.id,
            announcement.title,
            announcement.club.clubName,
            announcement.deadline,
            announcement.club.clubImage
        )
        FROM Announcement announcement
        """)
    List<AnnouncementResponse> findAllAnnouncements();
}
