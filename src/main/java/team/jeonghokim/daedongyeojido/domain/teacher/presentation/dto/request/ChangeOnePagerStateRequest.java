package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;

public record ChangeOnePagerStateRequest(
    @NotNull(message = "원페이져 상태를 선택해주세요.")
    OnePagerState onePagerState,
    
    String reason
) {
}
