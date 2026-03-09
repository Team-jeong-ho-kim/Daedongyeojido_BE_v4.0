package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;

import java.time.LocalDate;

public record ApplicationListResponse(
        Long submissionId,
        String clubName,
        String clubImage,
        ApplicationStatus applicationStatus,
        LocalDate submissionDuration
) {
    @QueryProjection
    public ApplicationListResponse(Long submissionId,
                                   String clubName,
                                   String clubImage,
                                   ApplicationStatus applicationStatus,
                                   LocalDate submissionDuration) {

        this.submissionId = submissionId;
        this.clubName = clubName;
        this.clubImage = clubImage;
        this.applicationStatus = applicationStatus;
        this.submissionDuration = submissionDuration;
    }
}
