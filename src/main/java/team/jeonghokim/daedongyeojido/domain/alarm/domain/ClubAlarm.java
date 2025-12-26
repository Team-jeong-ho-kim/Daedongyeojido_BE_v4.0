package team.jeonghokim.daedongyeojido.domain.alarm.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.global.entity.BaseTimeIdEntity;

@Entity
@Table(name = "tbl_club_alarm")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubAlarm extends BaseTimeIdEntity {

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType alarmType;

    @Builder
    public ClubAlarm(String title, String content, Club club, AlarmType alarmType) {
        this.title = title;
        this.content = content;
        this.club = club;
        this.alarmType = alarmType;
    }
}
