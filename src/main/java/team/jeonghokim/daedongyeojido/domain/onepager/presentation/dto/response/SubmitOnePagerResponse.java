package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;

import java.time.LocalDate;
import java.util.List;

public record SubmitOnePagerResponse(
    Long id,
    String clubName,
    OnePagerState onePagerState,
    String submitFileUrl,
    String submitFileName,
    LocalDate submitDate,
    List<SubmitCommentResponse> submitComments
) {
    public static SubmitOnePagerResponse of(
        SubmitOnePager submitOnePager,
        List<SubmitCommentResponse> submitComments
    ) {
        return new SubmitOnePagerResponse(
            submitOnePager.getId(),
            submitOnePager.getClub().getClubName(),
            submitOnePager.getOnePagerState(),
            submitOnePager.getSubmitFile() != null ? submitOnePager.getSubmitFile().getFileUrl() : null,
            submitOnePager.getSubmitFileName(),
            submitOnePager.getSubmitDate(),
            submitComments);
    }
}
