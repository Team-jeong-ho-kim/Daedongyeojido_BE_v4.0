package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerFileFormRequest;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerFileService {
    private final OnePagerRepository onePagerRepository;

    public void execute(OnePagerFileFormRequest onePagerFileFormRequest, Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
                .orElseThrow(() -> );
    }
}
