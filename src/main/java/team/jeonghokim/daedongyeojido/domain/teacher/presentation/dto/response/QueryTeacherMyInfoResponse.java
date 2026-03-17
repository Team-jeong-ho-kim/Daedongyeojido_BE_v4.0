package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;

@Builder
public record QueryTeacherMyInfoResponse(
        String accountId,
        String teacherName,
        Long clubId,
        String clubName
) {

    public static QueryTeacherMyInfoResponse from(Club club) {
        return QueryTeacherMyInfoResponse.builder()
                .accountId(club.getTeacher().getAccountId())
                .teacherName(club.getTeacher().getTeacherName())
                .clubId(club.getId())
                .clubName(club.getClubName())
                .build();
    }
}
