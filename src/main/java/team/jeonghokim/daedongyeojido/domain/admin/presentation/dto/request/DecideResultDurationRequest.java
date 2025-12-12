package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DecideResultDurationRequest(

        @NotNull(message = "발표 기간을 설정해주세요.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime resultDuration
) {
}
