package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.exception.FileNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.facade.TeacherFacade;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerFileFormRequest;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerFileService {
    private final OnePagerRepository onePagerRepository;
    private final TeacherFacade teacherFacade;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(OnePagerFileFormRequest request, Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
                .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        File file = fileRepository.findByFileName(request.formFile().getOriginalFilename())
                .orElseThrow(() -> FileNotFoundException.EXCEPTION);

        Teacher teacher = teacherFacade.getCurrentTeacher();

        onePager.update(
                request.title(),
                request.description(),
                file,
                null,
                teacher,
                request.onePagerDurationType(),
                request.onePagerDuration()
        );
    }
}
