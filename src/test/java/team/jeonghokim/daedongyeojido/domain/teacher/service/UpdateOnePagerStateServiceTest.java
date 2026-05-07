package team.jeonghokim.daedongyeojido.domain.teacher.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerInvalidException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerStateReasonInvalidException;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.ChangeOnePagerStateRequest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateOnePagerStateServiceTest {

    @Mock
    private SubmitOnePagerRepository submitOnePagerRepository;

    @InjectMocks
    private UpdateOnePagerStateService updateOnePagerStateService;

    @Test
    void canceledStateRequiresReason() {
        SubmitOnePager submitOnePager = submit(OnePagerState.SUBMITTED);
        when(submitOnePagerRepository.findById(1L)).thenReturn(Optional.of(submitOnePager));

        assertThrows(OnePagerStateReasonInvalidException.class,
                () -> updateOnePagerStateService.execute(new ChangeOnePagerStateRequest(OnePagerState.CANCELED, null), 1L));
    }

    @Test
    void onlySubmittedOrRejectedCanBeUpdated() {
        SubmitOnePager submitOnePager = submit(OnePagerState.APPROVED);
        when(submitOnePagerRepository.findById(1L)).thenReturn(Optional.of(submitOnePager));

        assertThrows(OnePagerInvalidException.class,
                () -> updateOnePagerStateService.execute(new ChangeOnePagerStateRequest(OnePagerState.REJECTED, "사유"), 1L));
    }

    @Test
    void rejectedStateStoresReason() {
        SubmitOnePager submitOnePager = submit(OnePagerState.SUBMITTED);
        when(submitOnePagerRepository.findById(1L)).thenReturn(Optional.of(submitOnePager));

        updateOnePagerStateService.execute(new ChangeOnePagerStateRequest(OnePagerState.REJECTED, "보완 필요"), 1L);

        assertEquals(OnePagerState.REJECTED, submitOnePager.getOnePagerState());
        assertEquals("보완 필요", submitOnePager.getReason());
    }

    private SubmitOnePager submit(OnePagerState state) {
        return SubmitOnePager.builder()
                .clubName("club")
                .onePagerState(state)
                .submitDate(LocalDate.now())
                .reason(null)
                .build();
    }
}
