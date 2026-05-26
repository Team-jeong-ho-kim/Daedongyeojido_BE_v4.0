package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerFileFormRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

@Service
@RequiredArgsConstructor
public class CreateOnePagerFileFormService {
    private final S3Service s3Service;
    private final CreateOnePagerFileFormTransactionService createOnePagerFileFormTransactionService;

    public Long execute(OnePagerFileFormRequest request) {
        String fileName = request.formFile().getOriginalFilename();
        String fileUrl = s3Service.upload(request.formFile(), FileType.DOCUMENT);

        return createOnePagerFileFormTransactionService.saveData(request,  fileName, fileUrl);
    }
}
