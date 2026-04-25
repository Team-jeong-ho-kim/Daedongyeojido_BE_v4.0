package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerUrlFormRequest;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerUrlService {
    private final OnePagerRepository onePagerRepository;

    @Transactional
    public void execute(OnePagerUrlFormRequest request, Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
                .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        onePager.update(
                request.title(),
                request.description(),
                null,
                request.formUrl(),
                request.teacherName(),
                request.onePagerDurationType(),
                request.onePagerDuration()
        );
    }
}
