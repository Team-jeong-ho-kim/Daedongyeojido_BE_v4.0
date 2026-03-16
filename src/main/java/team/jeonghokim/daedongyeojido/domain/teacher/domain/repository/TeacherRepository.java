package team.jeonghokim.daedongyeojido.domain.teacher.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long>, TeacherRepositoryCustom {

    Optional<Teacher> findByAccountId(String accountId);
}
