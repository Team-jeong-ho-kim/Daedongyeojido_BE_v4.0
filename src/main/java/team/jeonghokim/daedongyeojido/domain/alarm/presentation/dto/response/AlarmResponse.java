package team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.AdminAlarm;

public record AlarmResponse(
        Long id,
        String title,
        String content
) {
    @QueryProjection
    public AlarmResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static AlarmResponse from(AdminAlarm adminAlarm) {
        return new AlarmResponse(adminAlarm.getId(), adminAlarm.getTitle(), adminAlarm.getContent());
    }
}
