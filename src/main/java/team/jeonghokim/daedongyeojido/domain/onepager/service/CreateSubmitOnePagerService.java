package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidSubmitOnePagerException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateSubmitOnePagerService {
    private final OnePagerRepository onePagerRepository;
    private final SubmitOnePagerFileUploadService submitOnePagerFileUploadService;

    public void execute(SubmitOnePagerRequest request, Long formOnePagerId) {
        OnePager formOnePager = onePagerRepository.findById(formOnePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        if (formOnePager.getOnePagerDurationType() == OnePagerDurationType.DATE
            && formOnePager.getOnePagerDuration().isBefore(LocalDateTime.now())) {
            throw InvalidSubmitOnePagerException.EXCEPTION;
        }

        submitOnePagerFileUploadService.execute(request.submitFile(), formOnePager);
    }
}
