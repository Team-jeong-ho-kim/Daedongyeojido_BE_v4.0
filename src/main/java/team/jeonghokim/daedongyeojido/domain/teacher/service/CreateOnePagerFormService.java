package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.exception.AlreadyFileExistsException;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateOnePagerFormRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateOnePagerFormService {
    private final TeacherRepository teacherRepository;
    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @Transactional
    public void execute(CreateOnePagerFormRequest request) {
        String fileName = request.onePagerFile().getOriginalFilename();

        if (fileRepository.findByFileName(fileName).isPresent()) {
            throw AlreadyFileExistsException.EXCEPTION;
        }

        String fileUrl = s3Service.upload(request.onePagerFile(), FileType.DOCUMENT);
        String dueDate =  request.onePagerDuration().toString();


        File file = File.builder()
                .fileUrl(fileUrl)
                .fileName(fileName)
                .build();

        fileRepository.save(file);

        OnePager onePager = OnePager.builder()
                .title(request.title())
                .description(request.description())
                .fileName(fileName)
                .fileUrl(fileUrl)
                .teacherName(request.teacherName())
                .onePagerDuration(dueDate)
                .build();


    }
}
