package team.jeonghokim.daedongyeojido.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.UploadFileRequest;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.exception.AlreadyFileExistsException;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @Transactional
    public void execute(UploadFileRequest request) {
         if (fileRepository.findByFileName(request.fileName()).isPresent()) {
             throw AlreadyFileExistsException.EXCEPTION;
         }

         String filUrl = s3Service.upload(request.fileUrl(), FileType.DOCUMENT);
         File file = File.builder()
                 .fileUrl(filUrl)
                 .fileName(request.fileName())
                .build();

        fileRepository.save(file);
    }
}
