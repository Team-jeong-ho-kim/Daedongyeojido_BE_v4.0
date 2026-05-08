package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;

import java.time.LocalDate;

public record OnePagerSubmissionsResponse(
        Long onePagerFormId,
        String title,
        OnePagerState status,
        LocalDate submitDate
) {
    public static OnePagerSubmissionsResponse from(SubmitOnePager submission) {
        return new OnePagerSubmissionsResponse(
                submission.getId(),
                submission.getFormOnePager().getTitle(),
                submission.getOnePagerState(),
                submission.getSubmitDate()
        );
    }
}
