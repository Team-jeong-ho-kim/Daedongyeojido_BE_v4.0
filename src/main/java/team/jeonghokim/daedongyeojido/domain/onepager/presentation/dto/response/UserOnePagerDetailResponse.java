package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;

import java.time.LocalDateTime;

public record UserOnePagerDetailResponse(
    String title,
    String description,
    LocalDateTime onePagerDuration
) {
    public static UserOnePagerDetailResponse from(OnePager onePager) {
        return new UserOnePagerDetailResponse(
            onePager.getTitle(),
            onePager.getDescription(),
            onePager.getOnePagerDuration()
        );
    }
}
