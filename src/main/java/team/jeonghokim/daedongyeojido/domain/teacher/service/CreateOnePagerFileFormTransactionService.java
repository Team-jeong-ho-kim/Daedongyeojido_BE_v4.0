package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateOnePagerFileFormRequest;

@Service
@RequiredArgsConstructor
public class CreateOnePagerFileFormTransactionService {
    private final FileRepository fileRepository;
    private final OnePagerRepository onePagerRepository;

    @Transactional
    public void saveData(CreateOnePagerFileFormRequest request, String fileName, String fileUrl) {
        File file = File.builder()
                .fileUrl(fileUrl)
                .fileName(fileName)
                .build();

        fileRepository.save(file);

        OnePager onePager = OnePager.builder()
                .title(request.title())
                .description(request.description())
                .formFile(file)
                .formUrl(null)
                .teacherName(request.teacherName())
                .onePagerDuration(request.onePagerDuration())
                .build();

        onePagerRepository.save(onePager);
    }
}
