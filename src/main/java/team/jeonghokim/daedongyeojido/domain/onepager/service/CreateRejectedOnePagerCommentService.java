package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class CreateRejectedOnePagerCommentService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    @Transactional
    public void execute(CommentRequest request, Long submissionId) {
        SubmitOnePager submitOnePager = submitOnePagerRepository.findById(submissionId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);
        String commentWriter = getCurrentCommentWriter();

        RejectedOnePagerComment comment = RejectedOnePagerComment.builder()
            .comment(request.comment())
            .commentWriter(commentWriter)
            .onePager(submitOnePager)
            .build();

        rejectedOnePagerCommentRepository.save(comment);
    }

    private String getCurrentCommentWriter() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw UserNotFoundException.EXCEPTION;
        }

        String accountId = authentication.getName();
        boolean isTeacher = authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_TEACHER"));

        if (isTeacher) {
            return teacherRepository.findByAccountId(accountId)
                .orElseThrow(() -> TeacherNotFoundException.EXCEPTION)
                .getTeacherName();
        }

        return userRepository.findByAccountId(accountId)
            .orElseThrow(() -> UserNotFoundException.EXCEPTION)
            .getUserName();
    }
}
