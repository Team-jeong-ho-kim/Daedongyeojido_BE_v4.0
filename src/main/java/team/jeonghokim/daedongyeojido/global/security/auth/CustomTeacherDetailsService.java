package team.jeonghokim.daedongyeojido.global.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherNotFoundException;

@Component
@RequiredArgsConstructor
public class CustomTeacherDetailsService {

    private final TeacherRepository teacherRepository;

    public DaedongUserDetails loadTeacherByAccountId(String accountId) {
        Teacher teacher = teacherRepository.findByAccountId(accountId)
                .orElseThrow(() -> TeacherNotFoundException.EXCEPTION);

        return new CustomTeacherDetails(teacher);
    }
}
