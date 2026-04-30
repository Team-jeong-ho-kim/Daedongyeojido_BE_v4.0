package team.jeonghokim.daedongyeojido.domain.onepager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;

@Service
@RequiredArgsConstructor
public class CreateRejectedOnePagerCommentService {
    private final OnePagerRepository onePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;

    @Transactional
    public void execute(CommentRequest request) {
        OnePager onePager = onePagerRepository.findById(request.onePagerId())
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        RejectedOnePagerComment comment = RejectedOnePagerComment.builder()
            .comment(request.comment())
            .commentWriter(request.commentWriter())
            .onePager(onePager)
            .build();

        rejectedOnePagerCommentRepository.save(comment);
    }
}
