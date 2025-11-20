package team.jeonghokim.daedongyeojido.domain.submission.domain.repository;

import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationListResponse;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.ApplicantResponse;

import java.util.List;

public interface SubmissionRepositoryCustom {

    List<ApplicantResponse> findAllByApplicationFormIdWithValidStatuses(Long applicationFormId);

    List<ApplicationListResponse> findAllByUserId(Long userId);
}
