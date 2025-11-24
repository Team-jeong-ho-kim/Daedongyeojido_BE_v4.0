package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record QueryClubSubmissionDetailResponse(
        String userName,
        String classNumber,
        String introduction,
        Major major,
        List<SubmissionDto> answers
) {

    public static QueryClubSubmissionDetailResponse from(Submission submission) {
        return new QueryClubSubmissionDetailResponse(
                submission.getUserName(),
                submission.getClassNumber(),
                submission.getIntroduction(),
                submission.getMajor(),
                submission.getApplicationAnswers().stream().map(applicationAnswer ->
                        new SubmissionDto(
                                applicationAnswer.getApplicationQuestion().getId(),
                                applicationAnswer.getContent()
                        ))
                        .toList()
        );
    }
}
