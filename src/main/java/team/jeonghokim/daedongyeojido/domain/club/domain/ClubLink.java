package team.jeonghokim.daedongyeojido.domain.club.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity(name = "tbl_club_link")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubLink extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "link", length = 100)
    private String link;

    @Builder
    public ClubLink(Club club, String link) {
        this.club = club;
        this.link = link;
    }
}
