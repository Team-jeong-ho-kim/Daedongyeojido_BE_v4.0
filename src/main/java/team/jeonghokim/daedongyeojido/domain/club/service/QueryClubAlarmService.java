package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.AlarmResponse;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.QueryClubAlarmResponse;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.ClubAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryClubAlarmService {
    private final UserFacade userFacade;
    private final ClubFacade clubFacade;
    private final ClubAlarmRepository clubAlarmRepository;

    @Transactional(readOnly = true)
    public QueryClubAlarmResponse execute() {
        User user = userFacade.getCurrentUser();

        Club club = clubFacade.getClubById(user.getClub().getId());

        List<AlarmResponse> alarmResponses = clubAlarmRepository.findAllByClubId(club.getId());

        return new QueryClubAlarmResponse(alarmResponses);
    }
}
