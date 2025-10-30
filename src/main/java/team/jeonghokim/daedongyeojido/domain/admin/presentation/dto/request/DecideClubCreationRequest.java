package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DecideClubCreationRequest {

    @NotNull
    private boolean isOpen;
}
