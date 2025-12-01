package team.jeonghokim.daedongyeojido.domain.submission.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationListResponse;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.QApplicationListResponse;
import team.jeonghokim.daedongyeojido.domain.submission.domain.QSubmission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.*;

import java.util.List;
import java.util.Optional;

import static team.jeonghokim.daedongyeojido.domain.application.domain.QApplicationForm.applicationForm;
import static team.jeonghokim.daedongyeojido.domain.club.domain.QClub.club;
import static team.jeonghokim.daedongyeojido.domain.user.domain.QUser.user;

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

    @Override
    public Optional<Submission> findByUserIdAndClubId(Long userId, Long clubId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(submission)
                        .join(submission.user, user).fetchJoin()
                        .join(submission.applicationForm, applicationForm).fetchJoin()
                        .join(applicationForm.club, club).fetchJoin()
                        .where(
                                submission.user.id.eq(userId),
                                applicationForm.club.id.eq(clubId)
                        )
                        .fetchOne()
        );
    }
}
