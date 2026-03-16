package team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

@Builder
public record ClubCreationApplicantResponse(
        Long userId,
        String userName,
        String classNumber
) {
    public static ClubCreationApplicantResponse from(User user) {
        return ClubCreationApplicantResponse.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .classNumber(user.getClassNumber())
                .build();
    }
}
