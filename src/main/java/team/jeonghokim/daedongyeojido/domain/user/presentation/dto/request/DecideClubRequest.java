package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DecideClubRequest {

    @NotNull(message = "동아리 선택 여부를 결정해주세요.")
    private Boolean isSelected;

    @NotNull(message = "알람 ID를 입력해주세요.")
    private Long alarmId;
}
