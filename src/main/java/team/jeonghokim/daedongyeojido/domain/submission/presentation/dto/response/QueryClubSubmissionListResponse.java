package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import java.util.List;

public record QueryClubSubmissionListResponse(List<ApplicantResponse> applicants) {
}
