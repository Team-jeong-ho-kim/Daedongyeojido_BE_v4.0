package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.exception.AlreadyFileExistsException;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateOnePagerFormRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

@Service
@RequiredArgsConstructor
public class CreateOnePagerFileFormService {
    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final OnePagerRepository onePagerRepository;

    @Transactional
    public void execute(CreateOnePagerFormRequest request) {
        if(request.formFile())

        if (fileRepository.findByFileName(fileName).isPresent()) {
            throw AlreadyFileExistsException.EXCEPTION;
        }

        String fileName = request.onePagerFile().getOriginalFilename();

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
                .formFile(file)
                .formUrl(fileUrl)
                .teacherName(request.teacherName())
                .onePagerDuration(dueDate)
                .build();

        onePagerRepository.save(onePager);
    }
}
