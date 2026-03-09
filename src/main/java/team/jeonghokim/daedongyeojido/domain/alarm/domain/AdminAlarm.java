package team.jeonghokim.daedongyeojido.domain.alarm.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmCategory;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_admin_alarm")
@Getter
@NoArgsConstructor
public class AdminAlarm extends BaseIdEntity {

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType alarmType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmCategory alarmCategory;

    @Builder
    public AdminAlarm(String title, String content, AlarmType alarmType) {
        this.title = title;
        this.content = content;
        this.alarmType = alarmType;
        this.alarmCategory = alarmType.getCategory();
    }
}
