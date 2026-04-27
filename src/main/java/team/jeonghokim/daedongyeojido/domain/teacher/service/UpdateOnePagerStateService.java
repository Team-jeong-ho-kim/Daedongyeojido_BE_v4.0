package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerInvalidException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.ChangeOnePagerStateRequest;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerStateService {
    private final OnePagerRepository onePagerRepository;

    @Transactional
    public void execute(ChangeOnePagerStateRequest request,
                        Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        if(onePager.getFormFile() == null) {
            throw OnePagerInvalidException.EXCEPTION;
        }

        onePager.changeOnePagerState(request.onePagerState());
    }
}
