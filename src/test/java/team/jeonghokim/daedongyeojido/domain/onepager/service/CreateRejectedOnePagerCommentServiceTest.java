package team.jeonghokim.daedongyeojido.domain.onepager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRejectedOnePagerCommentServiceTest {

    @Mock
    private SubmitOnePagerRepository submitOnePagerRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;

    @InjectMocks
    private CreateRejectedOnePagerCommentService createRejectedOnePagerCommentService;

    @Test
    void existingWriterCreatesComment() {
        SubmitOnePager submitOnePager = SubmitOnePager.builder()
                .clubName("club")
                .onePagerState(OnePagerState.REJECTED)
                .submitDate(LocalDate.now())
                .build();

        when(submitOnePagerRepository.findById(1L)).thenReturn(Optional.of(submitOnePager));
        when(userRepository.existsByUserName("작성자")).thenReturn(true);

        createRejectedOnePagerCommentService.execute(new CommentRequest("보완하세요", "작성자"), 1L);

        ArgumentCaptor<RejectedOnePagerComment> captor = ArgumentCaptor.forClass(RejectedOnePagerComment.class);
        verify(rejectedOnePagerCommentRepository).save(captor.capture());
        assertEquals("보완하세요", captor.getValue().getComment());
        assertEquals("작성자", captor.getValue().getCommentWriter());
    }

    @Test
    void unknownWriterThrowsException() {
        SubmitOnePager submitOnePager = SubmitOnePager.builder()
                .clubName("club")
                .onePagerState(OnePagerState.REJECTED)
                .submitDate(LocalDate.now())
                .build();

        when(submitOnePagerRepository.findById(1L)).thenReturn(Optional.of(submitOnePager));
        when(userRepository.existsByUserName("작성자")).thenReturn(false);
        when(teacherRepository.existsByTeacherName("작성자")).thenReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> createRejectedOnePagerCommentService.execute(new CommentRequest("보완하세요", "작성자"), 1L));
    }
}
