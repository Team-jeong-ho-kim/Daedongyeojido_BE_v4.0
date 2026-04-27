package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.ChangeOnePagerStateRequest;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerStateService {
    private final OnePagerRepository onePagerRepository;

    public void execute(ChangeOnePagerStateRequest request) {
        OnePager onePager = onePagerRepository.findById(request.onePagerId())
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);


    }
}
