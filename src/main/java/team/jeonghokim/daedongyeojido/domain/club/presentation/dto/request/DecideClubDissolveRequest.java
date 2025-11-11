package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record DecideClubDissolveRequest(
        @JsonProperty("isDecision")
        @NotNull(message = "해체 신청 수락 및 거절 여부는 null일 수 없습니다.")
        Boolean isDecision
) {
}
