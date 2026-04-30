package team.jeonghokim.daedongyeojido.domain.onepager.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CreateRejectedOnePagerCommentService;

@RestController
@RequestMapping("/onepager")
@RequiredArgsConstructor
public class OnePagerController {
    private final CreateRejectedOnePagerCommentService createRejectedOnePagerCommentService;

    @PostMapping("/submissions/{submission-id}/comment")
    public void createComment(
        @RequestBody CommentRequest commentRequest,
        @PathVariable("submission-id") Long submissionId) {
    }
}
