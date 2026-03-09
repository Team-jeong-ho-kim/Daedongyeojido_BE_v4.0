package team.jeonghokim.daedongyeojido.domain.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;

public interface ApplicationQuestionRepository extends JpaRepository<ApplicationQuestion, Long> {
}
