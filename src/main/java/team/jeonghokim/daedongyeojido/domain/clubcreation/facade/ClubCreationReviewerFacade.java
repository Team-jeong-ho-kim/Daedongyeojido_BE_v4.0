package team.jeonghokim.daedongyeojido.domain.clubcreation.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ReviewerType;
import team.jeonghokim.daedongyeojido.domain.clubcreation.exception.ClubCreationReviewAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;

@Component
@RequiredArgsConstructor
public class ClubCreationReviewerFacade {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    public CurrentReviewer getCurrentReviewer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountId = authentication.getName();

        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_TEACHER"));

        if (isTeacher) {
            Teacher teacher = teacherRepository.findByAccountId(accountId)
                    .orElseThrow(() -> TeacherNotFoundException.EXCEPTION);

            return new CurrentReviewer(ReviewerType.TEACHER, teacher.getId(), teacher.getTeacherName());
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw ClubCreationReviewAccessDeniedException.EXCEPTION;
        }

        User admin = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        return new CurrentReviewer(ReviewerType.ADMIN, admin.getId(), admin.getUserName());
    }
}
