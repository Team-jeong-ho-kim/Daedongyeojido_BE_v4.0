package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import java.time.LocalDateTime;

public record UserOnePagerDetailResponse(
    String title,
    String description,
    LocalDateTime onePagerDuration,
    String fileUrl
) {
    public static UserOnePagerDetailResponse from(
        String title,
        String description,
        LocalDateTime onePagerDuration,
        String fileUrl
    ) {
        return new UserOnePagerDetailResponse(
            title, description, onePagerDuration, fileUrl);
    }
}
