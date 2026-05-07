package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.exception.AlreadyFileExistsException;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

@Service
@RequiredArgsConstructor
public class CreateSubmitOnePagerService {
    private final FileRepository fileRepository;
    private final OnePagerRepository onePagerRepository;
    private final S3Service s3Service;
    private final SubmitOnePagerFileUploadService submitOnePagerFileUploadService;

    public void execute(SubmitOnePagerRequest request, Long formOnePagerId) {
        OnePager formOnePager = onePagerRepository.findById(formOnePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        String fileName = request.submitFile().getName();

        fileRepository.findByFileName(fileName).ifPresent(file -> {
            throw AlreadyFileExistsException.EXCEPTION;
        });

        String fileUrl = s3Service.upload(request.submitFile(), FileType.DOCUMENT);
        submitOnePagerFileUploadService.execute(fileName, fileUrl, formOnePager);
    }
}
