package team.jeonghokim.daedongyeojido.domain.clubcreationform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.domain.ClubCreationForm;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.domain.repository.ClubCreationFormRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.exception.ClubCreationFormNotFoundException;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.presentation.dto.response.ClubCreationFormResponse;

@Service
@RequiredArgsConstructor
public class QueryClubCreationFormService {
    private final ClubCreationFormRepository clubCreationFormRepository;

    @Transactional(readOnly = true)
    public ClubCreationFormResponse execute() {

        ClubCreationForm clubCreationForm = clubCreationFormRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> ClubCreationFormNotFoundException.EXCEPTION);

        return new ClubCreationFormResponse(clubCreationForm.getFileName(), clubCreationForm.getFileUrl());
    }
}
