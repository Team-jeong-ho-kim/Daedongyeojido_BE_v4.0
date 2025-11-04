package team.jeonghokim.daedongyeojido.domain.club.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.ClubRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity(name = "tbl_club")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseIdEntity {

    @Column(name = "club_name", length = 20, nullable = false, unique = true)
    private String clubName;

    @Column(name = "club_image", length = 200, nullable = false)
    private String clubImage;

    @Column(name = "one_liner", length = 30, nullable = false)
    private String oneLiner;

    @Column(name = "introduction", length = 500, nullable = false)
    private String introduction;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private User clubApplicant;

    @Builder
    public Club(
            String clubName,
            String clubImage,
            String oneLiner,
            String introduction,
            Boolean isOpen,
            User clubApplicant
    ) {
        this.clubName = clubName;
        this.clubImage = clubImage;
        this.oneLiner = oneLiner;
        this.introduction = introduction;
        this.isOpen = isOpen;
        this.clubApplicant = clubApplicant;
    }


    public void clubOpen() {
        this.isOpen = true;
    }

    public void updateClub(ClubRequest request) {
        this.clubName = request.getClubName();
        this.clubImage = request.getClubImage();
        this.oneLiner = request.getOneLiner();
        this.introduction = request.getIntroduction();
    }
}
