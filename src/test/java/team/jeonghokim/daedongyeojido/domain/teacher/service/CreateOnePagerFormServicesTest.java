package team.jeonghokim.daedongyeojido.domain.teacher.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidDurationDateException;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerFileFormRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerUrlFormRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class CreateOnePagerFormServicesTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private OnePagerRepository onePagerRepository;

    @InjectMocks
    private CreateOnePagerFileFormTransactionService createOnePagerFileFormTransactionService;

    @InjectMocks
    private CreateOnePagerUrlFormService createOnePagerUrlFormService;

    @Test
    void fileFormDateMustBeFuture() {
        OnePagerFileFormRequest request = new OnePagerFileFormRequest(
                "title",
                "쌤",
                new MockMultipartFile("formFile", "form.pdf", "application/pdf", "data".getBytes()),
                OnePagerDurationType.DATE,
                LocalDateTime.now().minusDays(1),
                "desc"
        );

        assertThrows(InvalidDurationDateException.class,
                () -> createOnePagerFileFormTransactionService.saveData(request, "form.pdf", "url"));

        verifyNoInteractions(fileRepository, onePagerRepository);
    }

    @Test
    void fileFormInfinitySavesWithNullDuration() {
        OnePagerFileFormRequest request = new OnePagerFileFormRequest(
                "title",
                "쌤",
                new MockMultipartFile("formFile", "form.pdf", "application/pdf", "data".getBytes()),
                OnePagerDurationType.INFINITY,
                LocalDateTime.now().plusDays(1),
                "desc"
        );

        createOnePagerFileFormTransactionService.saveData(request, "form.pdf", "url");

        ArgumentCaptor<OnePager> captor = ArgumentCaptor.forClass(OnePager.class);
        verify(onePagerRepository).save(captor.capture());
        assertNull(captor.getValue().getOnePagerDuration());
    }

    @Test
    void urlFormDateMustBeFuture() {
        OnePagerUrlFormRequest request = new OnePagerUrlFormRequest(
                "title",
                "쌤",
                "https://example.com",
                OnePagerDurationType.DATE,
                LocalDateTime.now().minusDays(1),
                "desc"
        );

        assertThrows(InvalidDurationDateException.class,
                () -> createOnePagerUrlFormService.execute(request));
    }

    @Test
    void urlFormInfinitySavesWithNullDuration() {
        OnePagerUrlFormRequest request = new OnePagerUrlFormRequest(
                "title",
                "쌤",
                "https://example.com",
                OnePagerDurationType.INFINITY,
                LocalDateTime.now().plusDays(1),
                "desc"
        );

        createOnePagerUrlFormService.execute(request);

        ArgumentCaptor<OnePager> captor = ArgumentCaptor.forClass(OnePager.class);
        verify(onePagerRepository).save(captor.capture());
        assertNull(captor.getValue().getOnePagerDuration());
        assertEquals("https://example.com", captor.getValue().getFormUrl());
    }
}
