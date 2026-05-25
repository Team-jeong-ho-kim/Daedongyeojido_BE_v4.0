package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidUserException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerInvalidException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerStateReasonInvalidException;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.facade.TeacherFacade;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.ChangeOnePagerStateRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.UpdateStateReasonResponse;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerStateService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;
    private final TeacherFacade teacherFacade;

    @Transactional
    public UpdateStateReasonResponse execute(ChangeOnePagerStateRequest request, Long submitId) {
        Teacher teacher = teacherFacade.getCurrentTeacher();

        SubmitOnePager submitOnePager = submitOnePagerRepository.findById(submitId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        if(teacher != submitOnePager.getFormOnePager().getTeacher()){
            throw InvalidUserException.EXCEPTION;
        }

        OnePagerState targetState = request.status();
        validateReason(targetState, request.reason());

        submitOnePager.changeOnePagerState(targetState);

        String reason = requiresReason(targetState) ? request.reason() : null;

        if (reason != null) {
            // 사유는 제출의 별도 필드가 아니라 댓글(첫 댓글)로 저장한다.
            RejectedOnePagerComment reasonComment = RejectedOnePagerComment.builder()
                .comment(reason)
                .commentWriter(teacher.getTeacherName())
                .onePager(submitOnePager)
                .build();
            rejectedOnePagerCommentRepository.save(reasonComment);
        }

        return UpdateStateReasonResponse.of(reason);
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
