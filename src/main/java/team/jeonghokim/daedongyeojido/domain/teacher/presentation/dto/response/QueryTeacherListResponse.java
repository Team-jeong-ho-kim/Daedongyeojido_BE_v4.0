package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response;

import java.util.List;

public record QueryTeacherListResponse(List<TeacherResponse> teachers) {

    public static QueryTeacherListResponse from(List<TeacherResponse> teachers) {
        return new QueryTeacherListResponse(teachers);
    }
}
