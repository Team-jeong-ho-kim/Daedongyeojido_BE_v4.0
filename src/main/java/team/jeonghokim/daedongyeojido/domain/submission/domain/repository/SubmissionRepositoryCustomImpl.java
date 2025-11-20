package team.jeonghokim.daedongyeojido.domain.submission.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.ApplicantResponse;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QApplicantResponse;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.submission.domain.QSubmission.submission;

@RequiredArgsConstructor
public class SubmissionRepositoryCustomImpl implements SubmissionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ApplicantResponse> findAllByApplicationFormIdWithValidStatuses(Long applicationFormId) {
        return jpaQueryFactory
                .select(new QApplicantResponse(
                        submission.id,
                        submission.userName,
                        submission.classNumber,
                        submission.major
                ))
                .from(submission)
                .where(
                        submission.applicationForm.id.eq(applicationFormId),
                        submission.applicationStatus.in(
                                ApplicationStatus.SUBMITTED,
                                ApplicationStatus.ACCEPTED,
                                ApplicationStatus.REJECTED
                        )
                )
                .fetch();
    }
}
