package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.facade.TeacherFacade;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerFileFormRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

@Service
@RequiredArgsConstructor
public class UpdateOnePagerFileService {
    private final OnePagerRepository onePagerRepository;
    private final TeacherFacade teacherFacade;
    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @Transactional
    public void execute(OnePagerFileFormRequest request, Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
                .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);
        String fileUrl = s3Service.upload(request.formFile(), FileType.DOCUMENT);
        Teacher teacher = teacherFacade.getCurrentTeacher();
        String fileName = request.formFile().getOriginalFilename();
        File formFile = onePager.getFormFile();

        if (formFile == null) {
            formFile = File.builder()
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
        } else {
            formFile.update(fileName, fileUrl);
        }

        fileRepository.save(formFile);

        onePager.update(
                request.title(),
                request.description(),
                formFile,
                null,
                teacher,
                request.onePagerDurationType(),
                request.onePagerDuration()
        );
    }
}
