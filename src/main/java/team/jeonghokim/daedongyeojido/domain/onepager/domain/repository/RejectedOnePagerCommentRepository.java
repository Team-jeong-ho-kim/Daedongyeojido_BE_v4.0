package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;

public interface RejectedOnePagerCommentRepository extends JpaRepository<RejectedOnePagerComment, Long> {
}
