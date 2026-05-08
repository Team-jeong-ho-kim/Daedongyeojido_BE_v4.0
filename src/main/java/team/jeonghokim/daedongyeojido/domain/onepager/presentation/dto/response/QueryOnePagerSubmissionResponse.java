package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;

import java.util.List;

public record QueryOnePagerSubmissionResponse(
        String title,
        String teacherName,
        String fileUrl,
        String fileName,
        String myFileUrl,
        String myFileName,
        OnePagerState Status,
        List<RejectedOnePagerCommentResponse> comments
) {
    public static QueryOnePagerSubmissionResponse of(SubmitOnePager submission, List<RejectedOnePagerCommentResponse> commentsResponse) {
        return new QueryOnePagerSubmissionResponse(
                submission.getFormOnePager().getTitle(),
                submission.getFormOnePager().getTeacher().getTeacherName(),
                submission.getFormOnePager().getFormFileUrl(),
                submission.getFormOnePager().getFormFileName(),
                submission.getSubmitFileUrl(),
                submission.getSubmitFileName(),
                submission.getOnePagerState(),
                commentsResponse
        );
    }
}
