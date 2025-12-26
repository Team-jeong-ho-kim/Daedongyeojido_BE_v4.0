package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import java.util.List;

public record QueryUserSubmissionListResponse(List<SubmissionListResponse> submissions) {

    public static QueryUserSubmissionListResponse from(List<SubmissionListResponse> submissions) {
        return new QueryUserSubmissionListResponse(submissions);
    }
}
