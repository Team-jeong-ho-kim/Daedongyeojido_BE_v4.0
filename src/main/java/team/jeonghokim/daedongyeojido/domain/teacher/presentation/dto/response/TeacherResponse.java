package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.vo.TeacherMatchInfo;

public record TeacherResponse(
        Long teacherId,
        String teacherName,
        boolean matched
) {

    public static TeacherResponse from(TeacherMatchInfo teacher) {
        return new TeacherResponse(
                teacher.teacherId(),
                teacher.teacherName(),
                Boolean.TRUE.equals(teacher.matched())
        );
    }
}
