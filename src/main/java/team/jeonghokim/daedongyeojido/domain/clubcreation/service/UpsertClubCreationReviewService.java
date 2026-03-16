package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
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
    private final FinalizeClubCreationApplicationService finalizeClubCreationApplicationService;

    @Transactional
    public void execute(Long applicationId, UpsertClubCreationReviewRequest request) {
        ClubCreationApplication application = clubCreationApplicationFacade.getById(applicationId);

        if (!(application.getStatus() == ClubCreationApplicationStatus.SUBMITTED
                || application.getStatus() == ClubCreationApplicationStatus.UNDER_REVIEW)) {
            throw ClubCreationReviewAccessDeniedException.EXCEPTION;
        }

        CurrentReviewer currentReviewer = clubCreationReviewerFacade.getCurrentReviewer();
        upsertReview(application, currentReviewer, request);

        recalculate(application);
    }

    private void upsertReview(
            ClubCreationApplication application,
            CurrentReviewer currentReviewer,
            UpsertClubCreationReviewRequest request
    ) {
        clubCreationReviewRepository.findByApplicationAndRevisionAndReviewerTypeAndReviewerId(
                        application,
                        application.getRevision(),
                        currentReviewer.reviewerType(),
                        currentReviewer.reviewerId()
                )
                .ifPresentOrElse(
                        review -> review.update(request.decision(), request.feedback()),
                        () -> clubCreationReviewRepository.save(
                                ClubCreationReview.builder()
                                        .application(application)
                                        .reviewerType(currentReviewer.reviewerType())
                                        .reviewerId(currentReviewer.reviewerId())
                                        .reviewerName(currentReviewer.reviewerName())
                                        .revision(application.getRevision())
                                        .decision(request.decision())
                                        .feedback(request.feedback())
                                        .build()
                        )
                );
    }

    private void recalculate(ClubCreationApplication application) {
        List<ClubCreationReview> reviews = clubCreationReviewRepository
                .findByApplicationAndRevisionOrderByUpdatedAtAsc(application, application.getRevision());

        boolean hasRejected = reviews.stream()
                .anyMatch(review -> review.getDecision() == ClubCreationReviewDecision.REJECTED);

        if (hasRejected) {
            application.reject();
            return;
        }

        boolean hasChangesRequested = reviews.stream()
                .anyMatch(review -> review.getDecision() == ClubCreationReviewDecision.CHANGES_REQUESTED);

        boolean adminApproved = reviews.stream()
                .anyMatch(review -> review.getReviewerType() == ReviewerType.ADMIN
                        && review.getDecision() == ClubCreationReviewDecision.APPROVED);

        boolean teacherApproved = reviews.stream()
                .anyMatch(review -> review.getReviewerType() == ReviewerType.TEACHER
                        && review.getDecision() == ClubCreationReviewDecision.APPROVED);

        if (hasChangesRequested) {
            application.requestChanges();
            return;
        }

        if (adminApproved && teacherApproved) {
            finalizeClubCreationApplicationService.execute(application);
            return;
        }

        application.startReview();
    }
}
