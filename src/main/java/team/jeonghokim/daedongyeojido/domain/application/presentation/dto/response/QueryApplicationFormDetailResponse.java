package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.util.List;

@Builder
public record QueryApplicationFormDetailResponse(
        String applicationFormTitle,
        String clubName,
        String clubImage,
        List<ApplicationQuestionResponse> content,
        LocalDate submissionDuration,
        List<Major> major
) {
    public static QueryApplicationFormDetailResponse from(ApplicationForm applicationForm) {
        return QueryApplicationFormDetailResponse.builder()
                .applicationFormTitle(applicationForm.getApplicationFormTitle())
                .clubName(applicationForm.getClub().getClubName())
                .clubImage(applicationForm.getClub().getClubImage())
                .content(applicationForm.getApplicationQuestions().stream().map(ApplicationQuestionResponse::from).toList())
                .submissionDuration(applicationForm.getSubmissionDuration())
                .major(applicationForm.getApplicationMajors().stream().map(ApplicationMajor::getMajor).toList())
                .build();
    }
}
