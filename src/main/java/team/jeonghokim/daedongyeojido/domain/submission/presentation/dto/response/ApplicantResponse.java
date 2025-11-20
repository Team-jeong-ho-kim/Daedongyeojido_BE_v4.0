package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

public record ApplicantResponse(
        Long submissionId,
        String userName,
        String classNumber,
        Major major
) {

    @QueryProjection
    public ApplicantResponse(Long submissionId, String userName, String classNumber, Major major) {
        this.submissionId = submissionId;
        this.userName = userName;
        this.classNumber = classNumber;
        this.major = major;
    }
}
