package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidDurationDateException;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.facade.TeacherFacade;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerFileFormRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateOnePagerFileFormTransactionService {
    private final FileRepository fileRepository;
    private final TeacherFacade teacherFacade;
    private final OnePagerRepository onePagerRepository;

    @Transactional
    public Long saveData(OnePagerFileFormRequest request, String fileName, String fileUrl) {
        Teacher teacher = teacherFacade.getCurrentTeacher();

        if (request.onePagerDurationType() == OnePagerDurationType.DATE) {
            if (request.onePagerDuration() == null || request.onePagerDuration().isBefore(LocalDateTime.now())) {
                throw InvalidDurationDateException.EXCEPTION;
            }
        }

        File file = File.builder()
                .fileUrl(fileUrl)
                .fileName(fileName)
                .build();

        fileRepository.save(file);

        LocalDateTime date = request.onePagerDuration();

        if(request.onePagerDurationType() == OnePagerDurationType.INFINITY){
            date = null;
        }

        OnePager onePager = OnePager.builder()
                .title(request.title())
                .description(request.description())
                .formFile(file)
                .formUrl(null)
                .teacher(teacher)
                .onePagerDurationType(request.onePagerDurationType())
                .onePagerDuration(date)
                .build();

        onePagerRepository.save(onePager);

        return onePager.getId();
    }
}
