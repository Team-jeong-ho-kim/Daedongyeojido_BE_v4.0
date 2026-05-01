package team.jeonghokim.daedongyeojido.domain.onepager.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CreateRejectedOnePagerCommentService;

@RestController
@RequestMapping("/onepager")
@RequiredArgsConstructor
public class OnePagerController {
    private final CreateRejectedOnePagerCommentService createRejectedOnePagerCommentService;

    @PostMapping("/submissions/{submission-id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(
        @RequestBody CommentRequest request,
        @PathVariable("submission-id") Long submissionId
    ) {
        createRejectedOnePagerCommentService.execute(request, submissionId);
    }
}
