package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryUserSubmissionListResponse(List<SubmissionListResponse> submissions) {

    public static QueryUserSubmissionListResponse from(List<SubmissionListResponse> submissions) {
        return QueryUserSubmissionListResponse.builder()
                .submissions(submissions)
                .build();
    }
}
