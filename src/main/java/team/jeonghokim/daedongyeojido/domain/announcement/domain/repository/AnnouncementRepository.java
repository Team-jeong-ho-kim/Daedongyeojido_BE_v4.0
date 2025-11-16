package team.jeonghokim.daedongyeojido.domain.announcement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, AnnouncementCustomRepository {
}
