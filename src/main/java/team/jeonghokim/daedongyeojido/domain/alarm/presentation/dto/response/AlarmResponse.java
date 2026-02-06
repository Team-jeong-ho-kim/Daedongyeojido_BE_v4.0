package team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.AdminAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmCategory;

@Builder
public record AlarmResponse(
        Long id,
        String title,
        String content,
        AlarmCategory category
) {
    @QueryProjection
    public AlarmResponse(Long id, String title, String content, AlarmCategory category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public static AlarmResponse from(AdminAlarm adminAlarm) {
        return AlarmResponse.builder()
                .id(adminAlarm.getId())
                .title(adminAlarm.getTitle())
                .content(adminAlarm.getContent())
                .build();
    }
}
