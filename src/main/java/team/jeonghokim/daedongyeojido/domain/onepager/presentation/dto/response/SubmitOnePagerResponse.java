package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;

import java.time.LocalDate;
import java.util.List;

public record SubmitOnePagerResponse(
    String clubName,
    OnePagerState onePagerState,
    String submitFileUrl,
    LocalDate submitDate,
    List<SubmitCommentResponse> submitComments
) {
    public static SubmitOnePagerResponse of(
        SubmitOnePager submitOnePager,
        List<SubmitCommentResponse> submitComments
    ) {
        return new SubmitOnePagerResponse(
            submitOnePager.getClub().getClubName(),
            submitOnePager.getOnePagerState(),
            submitOnePager.getSubmitFile().getFileUrl(),
            submitOnePager.getSubmitDate(),
            submitComments);
    }
}
