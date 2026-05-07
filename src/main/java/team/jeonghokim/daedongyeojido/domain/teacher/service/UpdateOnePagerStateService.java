package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerInvalidException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerStateReasonInvalidException;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.ChangeOnePagerStateRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.UpdateStateReasonResponse;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerStateService {
    private final SubmitOnePagerRepository submitOnePagerRepository;

    @Transactional
    public UpdateStateReasonResponse execute(ChangeOnePagerStateRequest request, Long submitId) {
        SubmitOnePager submitOnePager = submitOnePagerRepository.findById(submitId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        OnePagerState targetState = request.onePagerState();
        validateUpdatable(submitOnePager);
        validateReason(targetState, request.reason());

        submitOnePager.changeOnePagerState(targetState);

        String reason = requiresReason(targetState) ? request.reason() : null;

        submitOnePager.setReason(reason);

        return UpdateStateReasonResponse.of(reason);
    }

    private void validateUpdatable(SubmitOnePager submitOnePager) { // 요청의 상태 검증
        OnePagerState currentState = submitOnePager.getOnePagerState();

        if (submitOnePager.getOnePagerState() == null
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
