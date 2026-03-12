package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherAlreadyExistsException;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateTeacherRequest;

@Service
@RequiredArgsConstructor
public class CreateTeacherService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(CreateTeacherRequest request) {
        teacherRepository.findByAccountId(request.accountId())
                .ifPresent(teacher -> {
                    throw TeacherAlreadyExistsException.EXCEPTION;
                });

        teacherRepository.save(
                Teacher.builder()
                        .accountId(request.accountId())
                        .teacherName(request.teacherName())
                        .password(passwordEncoder.encode(request.password()))
                        .build()
        );
    }
}
