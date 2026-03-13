package team.jeonghokim.daedongyeojido.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.exception.FileNotFoundException;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

@Service
@RequiredArgsConstructor
public class DeleteFileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @Transactional
    public void execute(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> FileNotFoundException.EXCEPTION);

        s3Service.delete(file.getFileUrl());
        fileRepository.delete(file);
    }
}
