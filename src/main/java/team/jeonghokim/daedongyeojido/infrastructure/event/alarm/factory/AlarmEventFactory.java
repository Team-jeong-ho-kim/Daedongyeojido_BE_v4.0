package team.jeonghokim.daedongyeojido.infrastructure.event.alarm.factory;

import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmCategory;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event.ClubAlarmEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event.UserAlarmEvent;

@Component
public class AlarmEventFactory {

    public UserAlarmEvent createUserAlarmEvent(
            User user,
            Club club,
            AlarmType alarmType
    ) {
        return createUserAlarmEvent(user, club, alarmType, null);
    }

    public UserAlarmEvent createUserAlarmEvent(
            User user,
            Club club,
            AlarmType alarmType,
            Long referenceId
    ) {
        return UserAlarmEvent.builder()
                .userId(user.getId())
                .alarmType(alarmType)
                .title(alarmType.formatTitle(club.getClubName()))
                .content(alarmType.formatContent(club.getClubName()))
                .category(alarmType.getCategory())
                .clubId(club.getId())
                .referenceId(referenceId)
                .build();
    }

    public ClubAlarmEvent createClubAlarmEvent(
            Club club,
            User user,
            AlarmType alarmType
    ) {
        return createClubAlarmEvent(club, user, alarmType, null);
    }

    public ClubAlarmEvent createClubAlarmEvent(
            Club club,
            User user,
            AlarmType alarmType,
            Long referenceId
    ) {
        return ClubAlarmEvent.builder()
                .clubId(club.getId())
                .alarmType(alarmType)
                .title(alarmType.formatTitle(user.getUserName()))
                .content(alarmType.formatContent(user.getUserName()))
                .category(alarmType.getCategory())
                .referenceId(referenceId)
                .build();
    }
}
