package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateResultDurationRequest(

        @NotNull(message = "발표기간을 입력해주세요.")
        @FutureOrPresent(message = "발표기간은 과거일 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime resultDuration
) {
}
