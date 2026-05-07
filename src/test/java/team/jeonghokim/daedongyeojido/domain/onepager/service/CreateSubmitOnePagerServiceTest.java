package team.jeonghokim.daedongyeojido.domain.onepager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidSubmitOnePagerException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateSubmitOnePagerServiceTest {

    @Mock
    private OnePagerRepository onePagerRepository;

    @Mock
    private SubmitOnePagerFileUploadService submitOnePagerFileUploadService;

    @InjectMocks
    private CreateSubmitOnePagerService createSubmitOnePagerService;

    @Test
    void urlFormCannotBeSubmitted() {
        OnePager onePager = OnePager.builder()
                .title("url form")
                .description("desc")
                .formFile(null)
                .formUrl("https://example.com")
                .teacherName("쌤")
                .onePagerDurationType(OnePagerDurationType.INFINITY)
                .build();

        when(onePagerRepository.findById(1L)).thenReturn(Optional.of(onePager));

        assertThrows(InvalidSubmitOnePagerException.class,
                () -> createSubmitOnePagerService.execute(request(), 1L));

        verifyNoInteractions(submitOnePagerFileUploadService);
    }

    @Test
    void expiredFormCannotBeSubmitted() {
        OnePager onePager = OnePager.builder()
                .title("file form")
                .description("desc")
                .formFile(file("form.pdf", "form-url"))
                .teacherName("쌤")
                .onePagerDurationType(OnePagerDurationType.DATE)
                .onePagerDuration(LocalDateTime.now().minusDays(1))
                .build();

        when(onePagerRepository.findById(1L)).thenReturn(Optional.of(onePager));

        assertThrows(InvalidSubmitOnePagerException.class,
                () -> createSubmitOnePagerService.execute(request(), 1L));

        verifyNoInteractions(submitOnePagerFileUploadService);
    }

    @Test
    void validFileFormDelegatesUpload() {
        OnePager onePager = OnePager.builder()
                .title("file form")
                .description("desc")
                .formFile(file("form.pdf", "form-url"))
                .teacherName("쌤")
                .onePagerDurationType(OnePagerDurationType.DATE)
                .onePagerDuration(LocalDateTime.now().plusDays(1))
                .build();
        SubmitOnePagerRequest request = request();

        when(onePagerRepository.findById(1L)).thenReturn(Optional.of(onePager));

        createSubmitOnePagerService.execute(request, 1L);

        verify(submitOnePagerFileUploadService).execute(request.submitFile(), onePager);
    }

    private SubmitOnePagerRequest request() {
        return new SubmitOnePagerRequest(
                new MockMultipartFile("submitFile", "submit.pdf", "application/pdf", "data".getBytes())
        );
    }

    private File file(String fileName, String fileUrl) {
        return File.builder()
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
    }
}
