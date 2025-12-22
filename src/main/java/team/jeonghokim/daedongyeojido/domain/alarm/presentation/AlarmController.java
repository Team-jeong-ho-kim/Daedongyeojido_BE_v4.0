package team.jeonghokim.daedongyeojido.domain.alarm.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.QueryClubAlarmResponse;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.QueryUserAlarmResponse;
import team.jeonghokim.daedongyeojido.domain.club.service.QueryClubAlarmService;
import team.jeonghokim.daedongyeojido.domain.user.service.QueryUserAlarmService;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final QueryClubAlarmService queryClubAlarmService;
    private final QueryUserAlarmService queryUserAlarmService;

    @GetMapping("/clubs")
    @ResponseStatus(HttpStatus.OK)
    public QueryClubAlarmResponse queryClubAlarm() {
        return queryClubAlarmService.execute();
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public QueryUserAlarmResponse queryUserAlarm() {
        return queryUserAlarmService.execute();
    }
}
