package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.domain.ClubCreationForm;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.domain.repository.ClubCreationFormRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.exception.ClubCreationFormNotFoundException;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

@Service
@RequiredArgsConstructor
public class DeleteClubCreationFormService {

    private final ClubCreationFormRepository clubCreationFormRepository;
    private final S3Service s3Service;

    @Transactional
    public void execute(Long clubCreationFormId) {

        ClubCreationForm clubCreationForm = clubCreationFormRepository.findById(clubCreationFormId)
                .orElseThrow(() -> ClubCreationFormNotFoundException.EXCEPTION);

        s3Service.delete(clubCreationForm.getFileUrl());

        clubCreationFormRepository.delete(clubCreationForm);
    }
}
