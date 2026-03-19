package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationReview;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationReviewDecision;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ReviewerType;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository.ClubCreationReviewRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreation.exception.ClubCreationReviewAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.clubcreation.facade.ClubCreationApplicationFacade;
import team.jeonghokim.daedongyeojido.domain.clubcreation.facade.ClubCreationReviewerFacade;
import team.jeonghokim.daedongyeojido.domain.clubcreation.facade.CurrentReviewer;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.request.UpsertClubCreationReviewRequest;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event.UserAlarmEvent;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UpsertClubCreationReviewService {

    private final ClubCreationApplicationFacade clubCreationApplicationFacade;
    private final ClubCreationReviewRepository clubCreationReviewRepository;
    private final ClubCreationReviewerFacade clubCreationReviewerFacade;
    private final ClubCreationReviewAccessService clubCreationReviewAccessService;
    private final FinalizeClubCreationApplicationService finalizeClubCreationApplicationService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void execute(Long applicationId, UpsertClubCreationReviewRequest request) {
        ClubCreationApplication application = clubCreationApplicationFacade.getById(applicationId);

        if (!(application.getStatus() == ClubCreationApplicationStatus.SUBMITTED
                || application.getStatus() == ClubCreationApplicationStatus.UNDER_REVIEW
                || application.getStatus() == ClubCreationApplicationStatus.CHANGES_REQUESTED)) {
            throw ClubCreationReviewAccessDeniedException.EXCEPTION;
        }

        CurrentReviewer currentReviewer = clubCreationReviewerFacade.getCurrentReviewer();
        clubCreationReviewAccessService.validateReviewerAccess(application, currentReviewer);
        upsertReview(application, currentReviewer, request);

        recalculate(application);
    }

    private void upsertReview(
            ClubCreationApplication application,
            CurrentReviewer currentReviewer,
            UpsertClubCreationReviewRequest request
    ) {
        findReview(application, currentReviewer)
                .ifPresentOrElse(
                        review -> review.update(request.decision(), request.feedback()),
                        () -> createReview(application, currentReviewer, request)
                );
    }

    private void createReview(
            ClubCreationApplication application,
            CurrentReviewer currentReviewer,
            UpsertClubCreationReviewRequest request
    ) {
        try {
            clubCreationReviewRepository.saveAndFlush(
                    ClubCreationReview.builder()
                            .application(application)
                            .reviewerType(currentReviewer.reviewerType())
                            .reviewerId(currentReviewer.reviewerId())
                            .reviewerName(currentReviewer.reviewerName())
                            .revision(application.getRevision())
                            .decision(request.decision())
                            .feedback(request.feedback())
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            findReview(application, currentReviewer)
                    .ifPresent(review -> review.update(request.decision(), request.feedback()));
        }
    }

    private java.util.Optional<ClubCreationReview> findReview(
            ClubCreationApplication application,
            CurrentReviewer currentReviewer
    ) {
        return clubCreationReviewRepository.findByApplicationAndRevisionAndReviewerTypeAndReviewerId(
                application,
                application.getRevision(),
                currentReviewer.reviewerType(),
                currentReviewer.reviewerId()
        );
    }

    private void recalculate(ClubCreationApplication application) {
        List<ClubCreationReview> reviews = clubCreationReviewRepository
                .findByApplicationOrderByRevisionAscUpdatedAtAsc(application);

        Map<ReviewerType, ClubCreationReviewDecision> latestDecisionByReviewerType = getLatestDecisionByReviewerType(reviews);

        ClubCreationReviewDecision adminDecision = latestDecisionByReviewerType.get(ReviewerType.ADMIN);
        ClubCreationReviewDecision teacherDecision = latestDecisionByReviewerType.get(ReviewerType.TEACHER);

        if (adminDecision == ClubCreationReviewDecision.REJECTED
                || teacherDecision == ClubCreationReviewDecision.REJECTED) {
            application.reject();
            eventPublisher.publishEvent(UserAlarmEvent.builder()
                    .userId(application.getApplicant().getId())
                    .alarmType(AlarmType.CLUB_CREATION_REJECTED)
                    .title(AlarmType.CLUB_CREATION_REJECTED.formatTitle(application.getClubName()))
                    .content(AlarmType.CLUB_CREATION_REJECTED.formatContent(application.getClubName()))
                    .category(AlarmType.CLUB_CREATION_REJECTED.getCategory())
                    .build());
            return;
        }

        if (adminDecision == ClubCreationReviewDecision.CHANGES_REQUESTED
                || teacherDecision == ClubCreationReviewDecision.CHANGES_REQUESTED) {
            application.requestChanges();
            return;
        }

        if (adminDecision == ClubCreationReviewDecision.APPROVED
                && teacherDecision == ClubCreationReviewDecision.APPROVED) {
            finalizeClubCreationApplicationService.execute(application);
            return;
        }

        application.startReview();
    }

    private Map<ReviewerType, ClubCreationReviewDecision> getLatestDecisionByReviewerType(List<ClubCreationReview> reviews) {
        Map<ReviewerType, ClubCreationReviewDecision> latestDecisionByReviewerType = new EnumMap<>(ReviewerType.class);

        reviews.forEach(review -> latestDecisionByReviewerType.put(
                review.getReviewerType(),
                review.getDecision()
        ));

        return latestDecisionByReviewerType;
    }
}
