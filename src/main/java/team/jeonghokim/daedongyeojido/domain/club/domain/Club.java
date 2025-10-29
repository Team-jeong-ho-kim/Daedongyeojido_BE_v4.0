package team.jeonghokim.daedongyeojido.domain.club.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "tbl_club")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseIdEntity {

    @Column(name = "club_name", length = 20, nullable = false)
    private String clubName;

    @Column(name = "club_image", length = 200, nullable = false)
    private String clubImage;

    @Column(name = "one_liner", length = 30, nullable = false)
    private String oneLiner;

    @Column(name = "introduction", length = 500, nullable = false)
    private String introduction;

    @Column(name = "is_opened", nullable = false)
    private boolean isOpened;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private User clubApplicant;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMajor> majors = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubLink> links = new ArrayList<>();

    @Builder
    public Club(String clubName, String clubImage, String oneLiner, String introduction, boolean isOpened, User clubApplicant) {
        this.clubName = clubName;
        this.clubImage = clubImage;
        this.oneLiner = oneLiner;
        this.introduction = introduction;
        this.isOpened = isOpened;
        this.clubApplicant = clubApplicant;
    }
}
