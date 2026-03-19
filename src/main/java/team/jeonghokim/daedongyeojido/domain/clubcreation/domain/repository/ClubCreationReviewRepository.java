package team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationReview;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ReviewerType;

import java.util.List;
import java.util.Optional;

public interface ClubCreationReviewRepository extends JpaRepository<ClubCreationReview, Long> {

    Optional<ClubCreationReview> findByApplicationAndRevisionAndReviewerTypeAndReviewerId(
            ClubCreationApplication application,
            int revision,
            ReviewerType reviewerType,
            Long reviewerId
    );

    List<ClubCreationReview> findByApplicationAndRevisionOrderByUpdatedAtAsc(
            ClubCreationApplication application,
            int revision
    );

    List<ClubCreationReview> findByApplicationOrderByRevisionAscUpdatedAtAsc(
            ClubCreationApplication application
    );
}
