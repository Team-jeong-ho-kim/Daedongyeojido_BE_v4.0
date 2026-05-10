package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.SubmitOnePagerAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.SubmitOnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.QueryOnePagerSubmissionResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.RejectedOnePagerCommentResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryMyOnePagerSubmissionDetailService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public QueryOnePagerSubmissionResponse execute(Long submissionId) {
        SubmitOnePager submission = submitOnePagerRepository.findById(submissionId)
                .orElseThrow(() -> SubmitOnePagerNotFoundException.EXCEPTION);

        User user = userFacade.getCurrentUser();
        if (!submission.getClub().getId().equals(user.getClub().getId())) {
            throw SubmitOnePagerAccessDeniedException.EXCEPTION;
        }

        List<RejectedOnePagerComment> comments = rejectedOnePagerCommentRepository.findAllByOnePagerId(submissionId);
        List<RejectedOnePagerCommentResponse> commentsResponse = comments.stream()
                .map(RejectedOnePagerCommentResponse::from)
                .toList();

        return QueryOnePagerSubmissionResponse.of(submission, commentsResponse);
    }
}
