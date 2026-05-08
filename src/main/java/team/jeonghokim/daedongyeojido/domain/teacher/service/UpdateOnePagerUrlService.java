package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.facade.TeacherFacade;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerUrlFormRequest;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerUrlService {
    private final OnePagerRepository onePagerRepository;
    private final TeacherFacade teacherFacade;

    @Transactional
    public void execute(OnePagerUrlFormRequest request, Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
                .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        Teacher teacher = teacherFacade.getCurrentTeacher();

        onePager.update(
                request.title(),
                request.description(),
                null,
                request.formUrl(),
                teacher,
                request.onePagerDurationType(),
                request.onePagerDuration()
        );
    }
}
