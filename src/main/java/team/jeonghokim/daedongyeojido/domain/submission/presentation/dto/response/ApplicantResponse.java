package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

public record ApplicantResponse(
        Long submissionId,
        String userName,
        String classNumber,
        String phoneNumber,
        Major major,
        ApplicationStatus clubApplicationStatus
) {

    @QueryProjection
    public ApplicantResponse(
            Long submissionId,
            String userName,
            String classNumber,
            String phoneNumber,
            Major major,
            ApplicationStatus clubApplicationStatus
    ) {
        this.submissionId = submissionId;
        this.userName = userName;
        this.classNumber = classNumber;
        this.phoneNumber = phoneNumber;
        this.major = major;
        this.clubApplicationStatus = clubApplicationStatus;
    }
}
