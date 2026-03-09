package team.jeonghokim.daedongyeojido.domain.club.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_club_link")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubLink extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(length = 100, nullable = false)
    private String link;

    @Builder
    public ClubLink(String link) {
        this.link = link;
    }

    protected void setClub(Club club) {
        if (this.club != null) {
            return;
        }
        this.club = club;
    }
}
