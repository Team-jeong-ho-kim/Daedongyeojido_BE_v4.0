package team.jeonghokim.daedongyeojido.domain.alarm.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.global.entity.BaseTimeIdEntity;

@Entity(name = "tbl_club_alarm")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
}
