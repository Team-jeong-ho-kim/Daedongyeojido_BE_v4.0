package team.jeonghokim.daedongyeojido.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserApplication extends BaseIdEntity {

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private Integer classNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(nullable = false)
    private boolean isApproved;
}
