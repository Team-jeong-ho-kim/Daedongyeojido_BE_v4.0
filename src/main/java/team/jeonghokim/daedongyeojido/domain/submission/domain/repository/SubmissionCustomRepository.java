package team.jeonghokim.daedongyeojido.domain.submission.domain.repository;

import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.ApplicantResponse;

import java.util.List;

public interface SubmissionCustomRepository {

    List<ApplicantResponse> findAllByApplicationFormWithValidStatuses(ApplicationForm applicationForm);
}
