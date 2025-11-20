package team.jeonghokim.daedongyeojido.domain.submission.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long>, SubmissionCustomRepository {
}
