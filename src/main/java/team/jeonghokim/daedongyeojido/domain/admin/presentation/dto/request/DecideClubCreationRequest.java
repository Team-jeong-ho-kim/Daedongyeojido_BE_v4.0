package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record DecideClubCreationRequest(
        @JsonProperty("isOpen")
        @NotNull(message = "개설 신청 수락 및 거절 여부는 null일 수 없습니다.")
        @JsonProperty("isApproved")
        Boolean isApproved
) {}
