package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateOnePagerFileFormRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

@Service
@RequiredArgsConstructor
public class CreateOnePagerUrlFormService {
    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final OnePagerRepository onePagerRepository;

    @Transactional
    public void execute(CreateOnePagerFileFormRequest request) {
        OnePager onePager = OnePager.builder()
                .title(request.title())
                .description(request.description())
                .formFile(null)
                .formUrl(request.)
                .teacherName(request.teacherName())
                .onePagerDuration(request.onePagerDuration())
                .build();

        onePagerRepository.save(onePager);
    }
}
