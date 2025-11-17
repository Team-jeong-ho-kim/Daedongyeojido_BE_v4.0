package team.jeonghokim.daedongyeojido.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity(name = "tbl_user_major")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMajor extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "major", nullable = false, length = 10)
    private Major major;

    @Builder
    public UserMajor(User user, Major major) {
        this.user = user;
        this.major = major;
    }

    protected void setUser(User user) {
        if (this.user != null) {
            return;
        }
        this.user = user;
    }
}
