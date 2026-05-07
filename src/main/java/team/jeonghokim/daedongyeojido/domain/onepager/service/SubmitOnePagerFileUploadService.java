package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.exception.AlreadyFileExistsException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

@Service
@RequiredArgsConstructor
public class SubmitOnePagerFileUploadService {
    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final CreateSubmitOnePagerService createSubmitOnePagerService;

    public void execute(SubmitOnePagerRequest request) {
        String fileName = request.submitFile().getName();

        fileRepository.findByFileName(fileName).ifPresent(file -> {
            throw AlreadyFileExistsException.EXCEPTION;
        });

        String fileUrl = s3Service.upload(request.submitFile(), FileType.DOCUMENT);
        createSubmitOnePagerService.execute(fileName, fileUrl);
    }
}
