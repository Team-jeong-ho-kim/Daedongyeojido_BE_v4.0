package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;

import java.util.List;

public interface RejectedOnePagerCommentRepository extends JpaRepository<RejectedOnePagerComment, Long> {
    List<RejectedOnePagerComment> findAllByOnePagerIdOrderByIdAsc(Long submissionId);

    List<RejectedOnePagerComment> findByOnePager(SubmitOnePager submitOnePager);

    List<RejectedOnePagerComment> findByOnePagerIn(List<SubmitOnePager> submitOnePager);
}
