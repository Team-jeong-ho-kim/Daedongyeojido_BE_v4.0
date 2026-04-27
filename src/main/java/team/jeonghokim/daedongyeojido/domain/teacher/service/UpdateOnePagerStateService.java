package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerStateService {
    private final OnePagerRepository onePagerRepository;

    public void execute(OnePagerState onePagerState) {  }
}
