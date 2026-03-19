package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;

@Builder
public record QueryTeacherMyInfoResponse(
        String accountId,
        String teacherName,
        Long clubId,
        String clubName
) {

    public static QueryTeacherMyInfoResponse of(Teacher teacher, Club club) {
        return QueryTeacherMyInfoResponse.builder()
                .accountId(teacher.getAccountId())
                .teacherName(teacher.getTeacherName())
                .clubId(club != null ? club.getId() : null)
                .clubName(club != null ? club.getClubName() : null)
                .build();
    }
}
