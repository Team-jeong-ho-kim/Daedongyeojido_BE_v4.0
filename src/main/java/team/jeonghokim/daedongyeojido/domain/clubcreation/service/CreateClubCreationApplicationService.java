package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyApplyClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyExistsClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyJoinClubException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.CreateClubRequest;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository.ClubCreationApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateClubCreationApplicationService {

    private final ClubCreationApplicationRepository clubCreationApplicationRepository;
    private final ClubRepository clubRepository;
    private final UserFacade userFacade;
    private final S3Service s3Service;

    @Transactional
    public void execute(CreateClubRequest request) {
        User applicant = userFacade.getCurrentUser();

        validate(request, applicant);

        ClubCreationApplication application = ClubCreationApplication.builder()
                .applicant(applicant)
                .clubName(request.clubName())
                .clubImage(request.clubImage() != null ? s3Service.upload(request.clubImage(), FileType.IMAGE) : "")
                .clubCreationForm(s3Service.upload(request.clubCreationForm(), FileType.DOCUMENT))
                .oneLiner(request.oneLiner())
                .introduction(request.introduction())
                .status(ClubCreationApplicationStatus.DRAFT)
                .revision(0)
                .majors(request.major())
                .links(request.link() == null ? List.of() : request.link())
                .build();

        application.submit();

        clubCreationApplicationRepository.save(application);
    }

    private void validate(CreateClubRequest request, User applicant) {
        if (applicant.getClub() != null) {
            throw AlreadyJoinClubException.EXCEPTION;
        }

        if (clubRepository.existsByClubName(request.clubName())) {
            throw AlreadyExistsClubException.EXCEPTION;
        }

        if (clubCreationApplicationRepository.existsByApplicantAndStatusIn(
                applicant,
                Set.of(
                        ClubCreationApplicationStatus.DRAFT,
                        ClubCreationApplicationStatus.SUBMITTED,
                        ClubCreationApplicationStatus.UNDER_REVIEW,
                        ClubCreationApplicationStatus.CHANGES_REQUESTED
                )
        )) {
            throw AlreadyApplyClubException.EXCEPTION;
        }

        if (clubCreationApplicationRepository.existsByClubNameAndStatusIn(
                request.clubName(),
                Set.of(
                        ClubCreationApplicationStatus.DRAFT,
                        ClubCreationApplicationStatus.SUBMITTED,
                        ClubCreationApplicationStatus.UNDER_REVIEW,
                        ClubCreationApplicationStatus.CHANGES_REQUESTED,
                        ClubCreationApplicationStatus.APPROVED
                )
        )) {
            throw AlreadyExistsClubException.EXCEPTION;
        }
    }
}
