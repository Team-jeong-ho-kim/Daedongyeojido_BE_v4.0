package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class CreateOnePagerFormService {
    private final TeacherRepository teacherRepository;

    public void execute() {

    }
}
