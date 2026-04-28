package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerInvalidException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerStateReasonInvalidException;
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

        OnePagerState targetState = request.onePagerState();
        validateUpdatable(onePager);
        validateReason(targetState, request.reason());

        onePager.changeOnePagerState(targetState);

        String reason = requiresReason(targetState) ? request.reason() : null;

        onePager.setReason(reason);

        return UpdateStateReasonResponse.of(reason);
    }

    private void validateUpdatable(OnePager onePager) { // 요청의 상태 검증
        OnePagerState currentState = onePager.getState();

        if (onePager.getFormFile() == null
                || (currentState != OnePagerState.SUBMITTED && currentState != OnePagerState.REJECTED)) {
            throw OnePagerInvalidException.EXCEPTION;
        }
    }

    private void validateReason(OnePagerState targetState, String reason) { // 반려됨, 거절됨의 상태일때 사유 미기재 검증
        if (requiresReason(targetState) && reason == null) {
            throw OnePagerStateReasonInvalidException.EXCEPTION;
        }
    }

    private boolean requiresReason(OnePagerState onePagerState) { // 반려됨, 거절됨 상태 판별
        return onePagerState == OnePagerState.CANCELED || onePagerState == OnePagerState.REJECTED;
    }
}
