package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;

public record ChangeOnePagerStateRequest(
    OnePagerState onePagerState
) {
}
