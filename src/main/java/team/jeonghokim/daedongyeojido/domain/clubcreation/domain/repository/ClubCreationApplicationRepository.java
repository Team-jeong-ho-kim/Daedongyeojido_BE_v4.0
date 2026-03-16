package team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ClubCreationApplicationRepository extends JpaRepository<ClubCreationApplication, Long> {

    boolean existsByApplicantAndStatusIn(User applicant, Collection<ClubCreationApplicationStatus> statuses);

    boolean existsByClubNameAndStatusIn(String clubName, Collection<ClubCreationApplicationStatus> statuses);

    @EntityGraph(attributePaths = {"applicant"})
    Optional<ClubCreationApplication> findTopByApplicantOrderByIdDesc(User applicant);

    @EntityGraph(attributePaths = {"applicant"})
    Optional<ClubCreationApplication> findWithApplicantById(Long applicationId);

    @EntityGraph(attributePaths = {"applicant"})
    List<ClubCreationApplication> findAllByOrderByIdDesc();
}
