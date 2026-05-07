package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.exception.AlreadyFileExistsException;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateSubmitOnePagerService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final UserFacade userFacade;

    @Transactional
    public void execute(SubmitOnePagerRequest request, Long onePagerId) {
        User submitUser = userFacade.getCurrentUser();

        String fileName = request.submitFile().getName();

        fileRepository.findByFileName(fileName).ifPresent(file -> {
            throw AlreadyFileExistsException.EXCEPTION;
        });

        String fileUrl = s3Service.upload(request.submitFile(), FileType.DOCUMENT);

        File file = File.builder()
            .fileUrl(fileUrl)
            .fileName(fileName)
            .build();

        fileRepository.save(file);

        String clubName = submitUser.getClub().getClubName();

        SubmitOnePager submitOnePager = SubmitOnePager.builder()
            .clubName(clubName)
            .onePagerState(OnePagerState.SUBMITTED)
            .submitFile(file)
            .submitDate(LocalDate.now())
            .build();

        submitOnePagerRepository.save(submitOnePager);
    }
}
