package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.SubmitOnePagerAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.SubmitOnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class CancelSubmitService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long submissionId) {
        SubmitOnePager submission = submitOnePagerRepository.findById(submissionId)
                .orElseThrow(() -> SubmitOnePagerNotFoundException.EXCEPTION);

        User user = userFacade.getCurrentUser();
        if (!submission.getClub().getId().equals(user.getClub().getId())) {
            throw SubmitOnePagerAccessDeniedException.EXCEPTION;
        }

        submission.cancel();
    }
}