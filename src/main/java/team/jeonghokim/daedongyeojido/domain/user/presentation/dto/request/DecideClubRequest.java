package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DecideClubRequest {

    @NotNull(message = "동아리 선택 여부를 결정해주세요.")
    @JsonProperty("isSelected")
    private Boolean isSelected;
}
