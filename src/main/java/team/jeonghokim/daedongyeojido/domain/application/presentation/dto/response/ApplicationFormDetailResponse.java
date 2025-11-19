package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.util.List;

public record ApplicationFormDetailResponse(
        String applicationFormTitle,
        String clubName,
        String clubImage,
        List<ApplicationQuestionResponse> content,
        LocalDate submissionDuration,
        List<Major> major
) {
    public static ApplicationFormDetailResponse of(ApplicationForm applicationForm) {
        return new ApplicationFormDetailResponse(
                applicationForm.getApplicationFormTitle(),
                applicationForm.getClub().getClubName(),
                applicationForm.getClub().getClubImage(),
                applicationForm.getApplicationQuestions().stream().map(ApplicationQuestionResponse::of).toList(),
                applicationForm.getSubmissionDuration(),
                applicationForm.getApplicationMajors().stream().map(ApplicationMajor::getMajor).toList()
        );
    }
}
