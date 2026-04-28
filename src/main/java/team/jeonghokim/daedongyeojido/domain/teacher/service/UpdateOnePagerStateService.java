package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerInvalidException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.ChangeOnePagerStateRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.UpdateStateReasonResponse;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerStateService {
    private final OnePagerRepository onePagerRepository;

    @Transactional
    public UpdateStateReasonResponse execute(ChangeOnePagerStateRequest request,
                                             Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        OnePagerState currentState = onePager.getState();

        if (onePager.getFormFile() == null
                || (currentState != OnePagerState.SUBMITTED && currentState != OnePagerState.REJECTED)) {
            throw OnePagerInvalidException.EXCEPTION;
        }

        onePager.changeOnePagerState(request.onePagerState());

        String reason = null;

        if(onePager.getState() == OnePagerState.CANCELED
            || onePager.getState() == OnePagerState.REJECTED) {
            if(request.reason() == null) {
                throw
            }
            reason = request.reason();
        }

        return UpdateStateReasonResponse.of(reason);
    }
}
