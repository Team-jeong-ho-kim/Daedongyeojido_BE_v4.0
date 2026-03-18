package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpsertClubCreationReviewService {

    private final ClubCreationApplicationFacade clubCreationApplicationFacade;
    private final ClubCreationReviewRepository clubCreationReviewRepository;
    private final ClubCreationReviewerFacade clubCreationReviewerFacade;
    private final ClubCreationReviewAccessService clubCreationReviewAccessService;
    private final FinalizeClubCreationApplicationService finalizeClubCreationApplicationService;

    @Transactional
    public void execute(Long applicationId, UpsertClubCreationReviewRequest request) {
        ClubCreationApplication application = clubCreationApplicationFacade.getById(applicationId);

        if (!(application.getStatus() == ClubCreationApplicationStatus.SUBMITTED
                || application.getStatus() == ClubCreationApplicationStatus.UNDER_REVIEW)) {
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
                .findByApplicationAndRevisionOrderByUpdatedAtAsc(application, application.getRevision());

        boolean hasChangesRequested = reviews.stream()
                .anyMatch(review -> review.getDecision() == ClubCreationReviewDecision.CHANGES_REQUESTED);

        boolean adminApproved = reviews.stream()
                .anyMatch(review -> review.getReviewerType() == ReviewerType.ADMIN
                        && review.getDecision() == ClubCreationReviewDecision.APPROVED);

        boolean teacherApproved = reviews.stream()
                .anyMatch(review -> review.getReviewerType() == ReviewerType.TEACHER
                        && review.getDecision() == ClubCreationReviewDecision.APPROVED);

        boolean adminRejected = reviews.stream()
                .anyMatch(review -> review.getReviewerType() == ReviewerType.ADMIN
                        && review.getDecision() == ClubCreationReviewDecision.REJECTED);

        boolean teacherRejected = reviews.stream()
                .anyMatch(review -> review.getReviewerType() == ReviewerType.TEACHER
                        && review.getDecision() == ClubCreationReviewDecision.REJECTED);

        if (hasChangesRequested) {
            application.requestChanges();
            return;
        }

        if (adminApproved && teacherApproved) {
            finalizeClubCreationApplicationService.execute(application);
            return;
        }

        if (adminRejected && teacherRejected) {
            application.reject();
            return;
        }

        application.startReview();
    }
}
