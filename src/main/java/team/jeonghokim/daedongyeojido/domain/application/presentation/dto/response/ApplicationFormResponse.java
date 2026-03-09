package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record ApplicationFormResponse(
        Long applicationFormId,
        String applicationFormTitle,
        String clubName,
        String clubImage,
        LocalDate submissionDuration
) {
    @QueryProjection
    public ApplicationFormResponse(Long applicationFormId,
                                   String applicationFormTitle,
                                   String clubName,
                                   String clubImage,
                                   LocalDate submissionDuration) {

        this.applicationFormId = applicationFormId;
        this.applicationFormTitle = applicationFormTitle;
        this.clubName = clubName;
        this.clubImage = clubImage;
        this.submissionDuration = submissionDuration;
    }
}
