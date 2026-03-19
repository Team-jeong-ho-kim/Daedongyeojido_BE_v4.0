package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.clubcreation.exception.CannotModifyClubCreationApplicationException;
import team.jeonghokim.daedongyeojido.domain.clubcreation.facade.ClubCreationApplicationFacade;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.request.UpdateClubCreationApplicationRequest;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateClubCreationApplicationService {

    private final ClubCreationApplicationFacade clubCreationApplicationFacade;
    private final UserFacade userFacade;
    private final S3Service s3Service;

    @Transactional
    public void execute(Long applicationId, UpdateClubCreationApplicationRequest request) {
        ClubCreationApplication application = clubCreationApplicationFacade.getById(applicationId);

        if (!application.getApplicant().getId().equals(userFacade.getCurrentUser().getId())) {
            throw CannotModifyClubCreationApplicationException.EXCEPTION;
        }

        if (!(application.getStatus() == ClubCreationApplicationStatus.DRAFT
                || application.getStatus() == ClubCreationApplicationStatus.CHANGES_REQUESTED)) {
            throw CannotModifyClubCreationApplicationException.EXCEPTION;
        }

        application.update(
                request.clubName(),
                request.clubImage() != null ? s3Service.upload(request.clubImage(), FileType.IMAGE) : null,
                request.clubCreationForm() != null ? s3Service.upload(request.clubCreationForm(), FileType.DOCUMENT) : null,
                request.oneLiner(),
                request.introduction(),
                request.major(),
                request.link() == null ? null : List.copyOf(request.link())
        );
    }
}
