package team.jeonghokim.daedongyeojido.domain.club.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity(name = "tbl_club_major")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMajor extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Enumerated(EnumType.STRING)
    @Column(name = "major", nullable = false, length = 10)
    private Major major;

    @Builder
    public ClubMajor(Club club, Major major) {
        this.club = club;
        this.major = major;
    }
}
