package team.jeonghokim.daedongyeojido.domain.club.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_club_major")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMajor extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Major major;

    @Builder
    public ClubMajor(Major major) {
        this.major = major;
    }

    protected void setClub(Club club) {
        if (this.club != null) {
            return;
        }
        this.club = club;
    }
}
