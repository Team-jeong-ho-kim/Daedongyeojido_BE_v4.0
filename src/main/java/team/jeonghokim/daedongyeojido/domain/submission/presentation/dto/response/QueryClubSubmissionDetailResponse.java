package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

@Builder
public record QueryClubSubmissionDetailResponse(
        String userName,
        String classNumber,
        String introduction,
        Major major,
        List<SubmissionDto> answers
) {

    public static QueryClubSubmissionDetailResponse from(Submission submission) {
        return QueryClubSubmissionDetailResponse.builder()
                .userName(submission.getUserName())
                .classNumber(submission.getClassNumber())
                .introduction(submission.getIntroduction())
                .major(submission.getMajor())
                .answers(submission.getApplicationAnswers().stream().map(SubmissionDto::from).toList())
                .build();
    }
}
