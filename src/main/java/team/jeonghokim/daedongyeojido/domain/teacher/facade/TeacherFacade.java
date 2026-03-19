package team.jeonghokim.daedongyeojido.domain.teacher.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherNotFoundException;

@Component
@RequiredArgsConstructor
public class TeacherFacade {

    private final TeacherRepository teacherRepository;

    public Teacher getCurrentTeacher() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        return teacherRepository.findByAccountId(accountId)
                .orElseThrow(() -> TeacherNotFoundException.EXCEPTION);
    }
}
