package team.jeonghokim.daedongyeojido.domain.teacher.domain.repository;

import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.vo.TeacherMatchInfo;

import java.util.List;

public interface TeacherRepositoryCustom {

    List<Teacher> findAllAvailableTeachers();

    List<TeacherMatchInfo> findAllTeachersWithMatchedStatus();
}
