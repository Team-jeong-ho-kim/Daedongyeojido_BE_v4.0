package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.exception.AlreadyFileExistsException;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidUserException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubmitOnePagerFileUploadService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final FileRepository fileRepository;
    private final UserFacade userFacade;
    private final S3Service s3Service;

    @Transactional
    public void execute(MultipartFile submitFile, OnePager onePager) {
        User submitUser = userFacade.getCurrentUser();

        Club club = submitUser.getClub();
        if (club == null) {
            throw InvalidUserException.EXCEPTION;
        }
        String clubName = club.getClubName();

        String fileName = submitFile.getOriginalFilename();

        fileRepository.findByFileName(fileName).ifPresent(f -> {
            throw AlreadyFileExistsException.EXCEPTION;
        });

        String fileUrl = s3Service.upload(submitFile, FileType.DOCUMENT);

        try {
            File file = File.builder()
                .fileUrl(fileUrl)
                .fileName(fileName)
                .build();

            fileRepository.save(file);

            SubmitOnePager submitOnePager = SubmitOnePager.builder()
                .clubName(clubName)
                .onePagerState(OnePagerState.SUBMITTED)
                .submitFile(file)
                .submitDate(LocalDate.now())
                .formOnePager(onePager)
                .build();

            submitOnePagerRepository.save(submitOnePager);
        } catch (Exception e) {
            s3Service.delete(fileUrl);
            throw e;
        }
    }
}
