package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryClubSubmissionListResponse(List<ApplicantResponse> applicants) {

    public static QueryClubSubmissionListResponse from(List<ApplicantResponse> applicants) {
        return QueryClubSubmissionListResponse.builder()
                .applicants(applicants)
                .build();
    }
}
