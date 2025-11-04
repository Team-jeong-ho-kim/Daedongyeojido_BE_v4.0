package team.jeonghokim.daedongyeojido.domain.user.test.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestUserMajor extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private TestUser user;

    @Enumerated(EnumType.STRING)
    @Column(name = "major", nullable = false, length = 10)
    private Major major;

    @Builder
    public TestUserMajor(TestUser user, Major major) {
        this.user = user;
        this.major = major;
    }
}
