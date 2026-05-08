package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;

import java.time.LocalDateTime;

public record OnePagerResponse(
    Long onePagerFormId,
    String title,
    String teacher,
    OnePagerDurationType onePagerDurationType,
    LocalDateTime onePagerDuration
) {
    public static OnePagerResponse from(OnePager onePager) {
        return new OnePagerResponse(
                onePager.getId(),
                onePager.getTitle(),
                onePager.getTeacherName(),
                onePager.getOnePagerDurationType(),
                onePager.getOnePagerDuration()
        );
    }
}
