package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;

import java.time.LocalDateTime;

public record OnePagerListResponse(
    Long onePagerFormId,
    String title,
    String teacher,
    OnePagerDurationType onePagerDurationType,
    LocalDateTime onePagerDuration
) {
    public static OnePagerListResponse from(OnePager onePager) {
        return new OnePagerListResponse(
                onePager.getId(),
                onePager.getTitle(),
                onePager.getTeacherName(),
                onePager.getOnePagerDurationType(),
                onePager.getOnePagerDuration()
        );
    }
}
