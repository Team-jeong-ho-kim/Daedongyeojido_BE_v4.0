package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import java.util.List;

public record OnePagerSubmissionListResponse(List<OnePagerSubmissionsResponse> submissions) {
    public static OnePagerSubmissionListResponse from(List<OnePagerSubmissionsResponse> submissions) {
        return new OnePagerSubmissionListResponse(submissions);
    }
}
