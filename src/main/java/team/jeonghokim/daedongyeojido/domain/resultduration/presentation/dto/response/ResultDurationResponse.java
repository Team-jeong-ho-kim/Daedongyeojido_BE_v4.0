package team.jeonghokim.daedongyeojido.domain.resultduration.presentation.dto.response;

import java.time.LocalDateTime;

public record ResultDurationResponse(

        Long id,
        LocalDateTime resultDuration
) {
}
