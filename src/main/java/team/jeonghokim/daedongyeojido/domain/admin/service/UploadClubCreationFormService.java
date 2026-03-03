package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.UploadClubCreationFormRequest;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.domain.ClubCreationForm;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.domain.repository.ClubCreationFormRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.exception.AlreadyClubCreationFormExistsException;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

@Service
@RequiredArgsConstructor
public class UploadClubCreationFormService {

    private final ClubCreationFormRepository clubCreationFormRepository;

    private final S3Service s3Service;

    @Transactional
    public void execute(UploadClubCreationFormRequest request) {

         if (clubCreationFormRepository.findByFileName(request.fileName()).isPresent()) {
             throw AlreadyClubCreationFormExistsException.EXCEPTION;
         }

         String filUrl = s3Service.upload(request.fileUrl(), FileType.DOCUMENT);

        ClubCreationForm clubCreationForm = ClubCreationForm.builder()
                        .fileUrl(filUrl)
                        .fileName(request.fileName())
                .build();

        clubCreationFormRepository.save(clubCreationForm);
    }
}
