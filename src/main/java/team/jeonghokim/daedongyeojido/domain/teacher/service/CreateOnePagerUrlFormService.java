package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateOnePagerUrlFormRequest;

@Service
@RequiredArgsConstructor
public class CreateOnePagerUrlFormService {
    private final OnePagerRepository onePagerRepository;

    @Transactional
    public void execute(CreateOnePagerUrlFormRequest request) {
        OnePager onePager = OnePager.builder()
                .title(request.title())
                .description(request.description())
                .formFile(null)
                .formUrl(request.formUrl())
                .teacherName(request.teacherName())
                .onePagerDuration(request.onePagerDuration())
                .build();

        onePagerRepository.save(onePager);
    }
}
