package team.jeonghokim.daedongyeojido.domain.submission.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationListResponse;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.QApplicationListResponse;
import team.jeonghokim.daedongyeojido.domain.submission.domain.QSubmission;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.*;

import java.util.List;

@RequiredArgsConstructor
public class SubmissionRepositoryCustomImpl implements SubmissionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QSubmission submission =  QSubmission.submission;

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

    @Override
    public List<ApplicationListResponse> findAllByUserId(Long userId) {
        return jpaQueryFactory
                .select(new QApplicationListResponse(
                        submission.id,
                        submission.applicationForm.club.clubName,
                        submission.applicationForm.club.clubImage,
                        submission.applicationStatus,
                        submission.applicationForm.submissionDuration
                ))
                .from(submission)
                .join(submission.applicationForm)
                .join(submission.applicationForm.club)
                .where(
                        submission.user.id.eq(userId),
                        submission.applicationStatus.eq(ApplicationStatus.WRITING)
                )
                .fetch();
    }

    @Override
    public List<SubmissionListResponse> findAllSubmissionByUserId(Long userId) {
        return jpaQueryFactory
                .select(new QSubmissionListResponse(
                        submission.id,
                        submission.applicationForm.club.clubName,
                        submission.applicationForm.club.clubImage,
                        submission.applicationStatus,
                        submission.applicationForm.submissionDuration
                ))
                .from(submission)
                .join(submission.applicationForm)
                .join(submission.applicationForm.club)
                .where(
                        submission.user.id.eq(userId),
                        submission.applicationStatus.in(
                                ApplicationStatus.SUBMITTED,
                                ApplicationStatus.ACCEPTED,
                                ApplicationStatus.REJECTED
                        )
                )
                .fetch();
    }
}
