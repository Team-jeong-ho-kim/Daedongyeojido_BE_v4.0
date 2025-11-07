package team.jeonghokim.daedongyeojido.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity(name = "tbl_user_link")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLink extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "link", length = 100, nullable = false)
    private String link;

    @Builder
    public UserLink(User user, String link) {
        this.user = user;
        this.link = link;
    }
}
