package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;

import java.time.LocalDateTime;
import java.util.List;

public record SubmitOnePagerResponse(
    String clubName,
    OnePagerState onePagerState,
    String submitFileUrl,
    LocalDateTime submitDate,
    List<SubmitCommentResponse> submitComments
) {
    public static SubmitOnePagerResponse of(
        String clubName,
        OnePagerState onePagerState,
        String submitFileUrl,
        LocalDateTime submitDate,
        List<SubmitCommentResponse> submitComments
    ) {
        return new SubmitOnePagerResponse(
            clubName, onePagerState, submitFileUrl, submitDate, submitComments);
    }
}
