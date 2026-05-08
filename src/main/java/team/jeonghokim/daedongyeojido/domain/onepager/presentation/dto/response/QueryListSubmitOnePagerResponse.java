package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import java.time.LocalDateTime;

public record QueryListSubmitOnePagerResponse(
    String title,
    String description,
    LocalDateTime onePagerDuration,
    String fileUrl

) {
}
