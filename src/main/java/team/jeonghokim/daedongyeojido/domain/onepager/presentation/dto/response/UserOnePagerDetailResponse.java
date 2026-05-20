package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;

import java.time.LocalDateTime;

public record UserOnePagerDetailResponse(
    String title,
    String description,
    LocalDateTime onePagerDuration,
    String fileUrl,
    String formUrl,
    OnePagerDurationType onePagerDurationType
) {
    public static UserOnePagerDetailResponse of(
        String title,
        String description,
        LocalDateTime onePagerDuration,
        String fileUrl,
        String formUrl,
        OnePagerDurationType onePagerDurationType
    ) {
        return new UserOnePagerDetailResponse(
            title, description, onePagerDuration, fileUrl,  formUrl,  onePagerDurationType);
    }
}
