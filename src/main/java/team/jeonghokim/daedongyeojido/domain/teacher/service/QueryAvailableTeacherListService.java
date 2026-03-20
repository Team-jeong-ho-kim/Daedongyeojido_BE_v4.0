package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherListResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.TeacherResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAvailableTeacherListService {

    private final TeacherRepository teacherRepository;

    @Transactional(readOnly = true)
    public QueryTeacherListResponse execute() {
        List<TeacherResponse> teachers = teacherRepository.findAllTeachersWithMatchedStatus().stream()
                .map(TeacherResponse::from)
                .toList();

        return QueryTeacherListResponse.from(teachers);
    }
}
