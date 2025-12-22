package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationQuestionAndAnswerResponse;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.util.List;

@Builder
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
    public static QueryApplicationDetailResponse from(Submission submission) {
        return QueryApplicationDetailResponse.builder()
                .clubName(submission.getApplicationForm().getClub().getClubName())
                .clubImage(submission.getApplicationForm().getClub().getClubImage())
                .userName(submission.getUserName())
                .classNumber(submission.getClassNumber())
                .introduction(submission.getIntroduction())
                .major(submission.getMajor())
                .contents(ApplicationQuestionAndAnswerResponse.from(submission))
                .submissionDuration(submission.getApplicationForm().getSubmissionDuration())
                .build();
    }
}
