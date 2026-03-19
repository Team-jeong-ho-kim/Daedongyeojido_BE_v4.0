package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository.ClubCreationApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository.ClubCreationReviewRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreation.exception.ClubCreationApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.ClubCreationApplicantResponse;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.ClubCreationApplicationDetailResponse;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.ClubCreationReviewResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryMyClubCreationApplicationService {

    private final ClubCreationApplicationRepository clubCreationApplicationRepository;
    private final ClubCreationReviewRepository clubCreationReviewRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public ClubCreationApplicationDetailResponse execute() {
        User currentUser = userFacade.getCurrentUser();

        ClubCreationApplication application = clubCreationApplicationRepository.findTopByApplicantOrderByIdDesc(currentUser)
                .orElseThrow(() -> ClubCreationApplicationNotFoundException.EXCEPTION);

        return toResponse(application);
    }

    ClubCreationApplicationDetailResponse toResponse(ClubCreationApplication application) {
        List<ClubCreationReviewResponse> currentReviews = clubCreationReviewRepository
                .findByApplicationAndRevisionOrderByUpdatedAtAsc(application, application.getRevision())
                .stream()
                .map(ClubCreationReviewResponse::from)
                .toList();

        List<ClubCreationReviewResponse> reviewHistory = clubCreationReviewRepository
                .findByApplicationOrderByRevisionAscUpdatedAtAsc(application)
                .stream()
                .map(ClubCreationReviewResponse::from)
                .toList();

        return ClubCreationApplicationDetailResponse.of(
                application,
                ClubCreationApplicantResponse.from(application.getApplicant()),
                currentReviews,
                reviewHistory
        );
    }
}
