package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidUserException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubmitOnePagerFileUploadService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final FileRepository fileRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(String fileName, String fileUrl, OnePager onePager) {
        User submitUser = userFacade.getCurrentUser();

        File file = File.builder()
            .fileUrl(fileUrl)
            .fileName(fileName)
            .build();

        fileRepository.save(file);

        String clubName = submitUser.getClub().getClubName();

        if(clubName == null) {
            throw InvalidUserException.EXCEPTION;
        }

        SubmitOnePager submitOnePager = SubmitOnePager.builder()
            .clubName(clubName)
            .onePagerState(OnePagerState.SUBMITTED)
            .submitFile(file)
            .submitDate(LocalDate.now())
            .formOnePager(onePager)
            .build();

        submitOnePagerRepository.save(submitOnePager);
    }
}
