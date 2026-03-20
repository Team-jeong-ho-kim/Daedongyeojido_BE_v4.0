package team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.vo;

public record TeacherMatchInfo(
        Long teacherId,
        String teacherName,
        Boolean matched
) {
}
