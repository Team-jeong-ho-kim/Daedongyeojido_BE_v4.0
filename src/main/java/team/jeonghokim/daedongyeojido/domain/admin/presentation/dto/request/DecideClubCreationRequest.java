package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DecideClubCreationRequest {

    @NotNull(message = "개설 신청 수락 및 거절 여부는 null일 수 없습니다.")
    @JsonProperty("isOpen")
    private Boolean isOpen;
}
