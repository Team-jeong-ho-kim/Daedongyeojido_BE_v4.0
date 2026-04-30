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
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CreateRejectedOnePagerCommentService {
    private final OnePagerRepository onePagerRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;

    @Transactional
    public void execute(CommentRequest request, Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        teacherRepository.findByTeacherName(request.commentWriter())
            .orElseThrow(() -> TeacherNotFoundException.EXCEPTION);

        if(
            userRepository.
        )

        RejectedOnePagerComment comment = RejectedOnePagerComment.builder()
            .comment(request.comment())
            .commentWriter(request.commentWriter())
            .onePager(onePager)
            .build();

        rejectedOnePagerCommentRepository.save(comment);
    }
}
