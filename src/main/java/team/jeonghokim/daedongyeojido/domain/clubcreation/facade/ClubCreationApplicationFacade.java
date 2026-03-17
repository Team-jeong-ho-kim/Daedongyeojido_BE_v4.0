package team.jeonghokim.daedongyeojido.domain.clubcreation.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository.ClubCreationApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreation.exception.ClubCreationApplicationNotFoundException;

@Component
@RequiredArgsConstructor
public class ClubCreationApplicationFacade {

    private final ClubCreationApplicationRepository clubCreationApplicationRepository;

    public ClubCreationApplication getById(Long applicationId) {
        return clubCreationApplicationRepository.findById(applicationId)
                .orElseThrow(() -> ClubCreationApplicationNotFoundException.EXCEPTION);
    }
}
