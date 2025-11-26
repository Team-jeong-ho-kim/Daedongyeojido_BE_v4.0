package team.jeonghokim.daedongyeojido.domain.schedule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_schedule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseIdEntity {

    @Column(nullable = false)
    private LocalDate interviewSchedule;

    @Column(nullable = false, length = 20)
    private String place;

    @Column(nullable = false)
    private LocalDateTime interviewTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Builder
    public Schedule(LocalDate interviewSchedule, String place, LocalDateTime interviewTime, User user, Club club) {
        this.interviewSchedule = interviewSchedule;
        this.place = place;
        this.interviewTime = interviewTime;
        this.user = user;
        this.club = club;
    }
}
