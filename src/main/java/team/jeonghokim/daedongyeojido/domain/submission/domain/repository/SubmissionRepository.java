package team.jeonghokim.daedongyeojido.domain.submission.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long>, SubmissionRepositoryCustom {

    Optional<Submission> findByUserIdAndApplicationFormClubId(Long userId, Long applicationFormClubId);
}
