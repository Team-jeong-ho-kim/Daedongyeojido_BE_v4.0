package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidDurationDateException;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.facade.TeacherFacade;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerUrlFormRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateOnePagerUrlFormService {
    private final OnePagerRepository onePagerRepository;
    private final TeacherFacade teacherFacade;

    @Transactional
    public Long execute(OnePagerUrlFormRequest request) {
        Teacher teacher = teacherFacade.getCurrentTeacher();

        if (request.onePagerDurationType() == OnePagerDurationType.DATE) {
            if (request.onePagerDuration() == null || request.onePagerDuration().isBefore(LocalDateTime.now())) {
                throw InvalidDurationDateException.EXCEPTION;
            }
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
                .teacher(teacher)
                .onePagerDurationType(request.onePagerDurationType())
                .onePagerDuration(date)
                .build();

        onePagerRepository.save(onePager);
        
        return onePager.getId();
    }
}
