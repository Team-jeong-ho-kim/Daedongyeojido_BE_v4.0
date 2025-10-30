package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DecideClubCreationRequest {

    @NotNull
    @JsonProperty("isOpen")
    private boolean isOpen;
}
