package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.facade.TeacherFacade;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherMyInfoResponse;

@Service
@RequiredArgsConstructor
public class QueryTeacherMyInfoService {

    private final TeacherFacade teacherFacade;
    private final ClubRepository clubRepository;

    @Transactional(readOnly = true)
    public QueryTeacherMyInfoResponse execute() {
        Teacher teacher = teacherFacade.getCurrentTeacher();
        Club club = clubRepository.findByTeacherAccountId(teacher.getAccountId());

        return QueryTeacherMyInfoResponse.of(teacher, club);
    }
}
