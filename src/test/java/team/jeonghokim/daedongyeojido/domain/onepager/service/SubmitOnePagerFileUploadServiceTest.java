package team.jeonghokim.daedongyeojido.domain.onepager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidUserException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubmitOnePagerFileUploadServiceTest {

    @Mock
    private SubmitOnePagerRepository submitOnePagerRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private UserFacade userFacade;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private SubmitOnePagerFileUploadService submitOnePagerFileUploadService;

    @Test
    void userWithoutClubCannotSubmit() {
        User user = User.builder()
                .accountId("student1")
                .userName("홍길")
                .password("pw")
                .classNumber("1101")
                .role(Role.STUDENT)
                .build();

        when(userFacade.getCurrentUser()).thenReturn(user);

        assertThrows(InvalidUserException.class,
                () -> submitOnePagerFileUploadService.execute(file(), onePager()));

        verify(s3Service, never()).upload(any(), any());
    }

    @Test
    void validClubUserCreatesFileAndSubmission() {
        Club club = Club.builder()
                .clubName("test-club")
                .clubImage("image")
                .clubCreationForm("form")
                .oneLiner("line")
                .introduction("intro")
                .build();
        User user = User.builder()
                .accountId("leader1")
                .userName("부장")
                .password("pw")
                .classNumber("1101")
                .role(Role.CLUB_LEADER)
                .club(club)
                .build();
        MockMultipartFile submitFile = file();
        OnePager onePager = onePager();

        when(userFacade.getCurrentUser()).thenReturn(user);
        when(fileRepository.findByFileName("submit.pdf")).thenReturn(Optional.empty());
        when(s3Service.upload(submitFile, FileType.DOCUMENT)).thenReturn("https://s3/submit.pdf");

        submitOnePagerFileUploadService.execute(submitFile, onePager);

        ArgumentCaptor<File> fileCaptor = ArgumentCaptor.forClass(File.class);
        ArgumentCaptor<SubmitOnePager> submitCaptor = ArgumentCaptor.forClass(SubmitOnePager.class);

        verify(fileRepository).save(fileCaptor.capture());
        verify(submitOnePagerRepository).save(submitCaptor.capture());

        assertEquals("submit.pdf", fileCaptor.getValue().getFileName());
        assertEquals("https://s3/submit.pdf", fileCaptor.getValue().getFileUrl());
        assertEquals("test-club", submitCaptor.getValue().getClubName());
        assertEquals(onePager, submitCaptor.getValue().getFormOnePager());
    }

    private MockMultipartFile file() {
        return new MockMultipartFile("submitFile", "submit.pdf", "application/pdf", "data".getBytes());
    }

    private OnePager onePager() {
        return OnePager.builder()
                .title("title")
                .description("desc")
                .formFile(File.builder().fileName("form.pdf").fileUrl("form-url").build())
                .teacherName("쌤")
                .onePagerDurationType(OnePagerDurationType.INFINITY)
                .build();
    }
}
