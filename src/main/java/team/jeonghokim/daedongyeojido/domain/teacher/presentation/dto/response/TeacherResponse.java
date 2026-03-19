package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;

public record TeacherResponse(
        Long teacherId,
        String teacherName
) {

    public static TeacherResponse from(Teacher teacher) {
        return new TeacherResponse(
                teacher.getId(),
                teacher.getTeacherName()
        );
    }
}
