package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;

import java.time.LocalDateTime;

public record SubmitOnePagerResponse(
    String clubName,
    OnePagerState onePagerState,
    String submitFileUrl,
    LocalDateTime submitDate,
    List<>
) {
}
