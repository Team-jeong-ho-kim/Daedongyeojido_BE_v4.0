package team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;

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
}
