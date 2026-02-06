package team.jeonghokim.daedongyeojido.domain.alarm.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmCategory;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.global.entity.BaseTimeIdEntity;

@Entity
@Table(name = "tbl_user_alarm")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAlarm extends BaseTimeIdEntity {

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType alarmType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmCategory alarmCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Builder
    public UserAlarm(String title, String content, User receiver, AlarmType alarmType, Club club) {
        this.title = title;
        this.content = content;
        this.receiver = receiver;
        this.alarmType = alarmType;
        this.alarmCategory = alarmType.getCategory();
        this.club = club;
    }
}
