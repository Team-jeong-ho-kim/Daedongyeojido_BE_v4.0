package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateSubmitOnePagerService {
    private final OnePagerRepository onePagerRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(SubmitOnePagerRequest request) {
        User submitUser = userFacade.getCurrentUser();

        String clubName = submitUser.getClub().getClubName();

        SubmitOnePager submitOnePager = SubmitOnePager.builder()
            .clubName(clubName)
            .onePagerState(OnePagerState.SUBMITTED)
            .submitFile(request.submitFile())
            .submitDate(LocalDate.now())
            .build();


    }
}
