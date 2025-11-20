package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationQuestionAndAnswerResponse;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.util.List;

public record QueryApplicationDetailResponse(
        String clubName,
        String clubImage,
        String userName,
        String classNumber,
        String introduction,
        Major major,
        List<ApplicationQuestionAndAnswerResponse> contents,
        LocalDate submissionDuration
) {
    public static QueryApplicationDetailResponse of(Submission submission) {
        return new QueryApplicationDetailResponse(
                submission.getApplicationForm().getClub().getClubName(),
                submission.getApplicationForm().getClub().getClubImage(),
                submission.getUserName(),
                submission.getClassNumber(),
                submission.getIntroduction(),
                submission.getMajor(),
                ApplicationQuestionAndAnswerResponse.from(submission),
                submission.getApplicationForm().getSubmissionDuration()
        );
    }
}
