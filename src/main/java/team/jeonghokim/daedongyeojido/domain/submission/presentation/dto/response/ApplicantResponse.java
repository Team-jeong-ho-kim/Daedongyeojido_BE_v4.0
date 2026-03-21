package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.submission.domain.enums.InterviewStatus;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

public record ApplicantResponse(
        Long submissionId,
        String userName,
        String classNumber,
        Major major,
        ApplicationStatus clubApplicationStatus,
        InterviewStatus interviewStatus
) {

    @QueryProjection
    public ApplicantResponse(
            Long submissionId,
            String userName,
            String classNumber,
            Major major,
            ApplicationStatus clubApplicationStatus,
            InterviewStatus interviewStatus
    ) {
        this.submissionId = submissionId;
        this.userName = userName;
        this.classNumber = classNumber;
        this.major = major;
        this.clubApplicationStatus = clubApplicationStatus;
        this.interviewStatus = interviewStatus;
    }
}
