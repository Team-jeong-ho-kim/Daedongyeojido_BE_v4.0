package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidDurationDateException;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateOnePagerUrlFormRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateOnePagerUrlFormService {
    private final OnePagerRepository onePagerRepository;

    @Transactional
    public void execute(CreateOnePagerUrlFormRequest request) {
        if (request.onePagerDurationType() == OnePagerDurationType.DATE
                && request.onePagerDuration().isBefore(LocalDateTime.now())) {
            throw InvalidDurationDateException.EXCEPTION;
        }

        LocalDateTime date = request.onePagerDuration();

        if(request.onePagerDurationType() == OnePagerDurationType.INFINITY){
            date = null;
        }

        OnePager onePager = OnePager.builder()
                .title(request.title())
                .description(request.description())
                .formFile(null)
                .formUrl(request.formUrl())
                .teacherName(request.teacherName())
                .onePagerDurationType(request.onePagerDurationType())
                .onePagerDuration(date)
                .build();

        onePagerRepository.save(onePager);
    }
}
