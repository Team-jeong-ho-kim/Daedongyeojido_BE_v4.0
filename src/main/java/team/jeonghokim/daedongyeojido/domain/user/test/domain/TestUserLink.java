package team.jeonghokim.daedongyeojido.domain.user.test.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestUserLink extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private TestUser user;

    @Column(name = "link", length = 100, nullable = false)
    private String link;

    @Builder
    public TestUserLink(TestUser user, String link) {
        this.user = user;
        this.link = link;
    }
}
