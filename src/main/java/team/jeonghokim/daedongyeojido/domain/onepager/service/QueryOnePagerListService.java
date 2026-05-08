package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.OnePagerResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryOnePagerListService {
    private final OnePagerRepository onePagerRepository;

    @Transactional(readOnly = true)
    public List<OnePagerResponse> execute() {
        List<OnePager> onePagers = onePagerRepository.findAllByOrderByIdDesc();
        return onePagers.stream()
                .map(OnePagerResponse::from)
                .toList();
    }
}
