package team.jeonghokim.daedongyeojido.domain.onepager.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_submit_onepager")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubmitOnePager extends BaseIdEntity {
    @JoinColumn(name = "submit_club_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 9)
    private OnePagerState onePagerState;

    @Column(nullable = false)
    private LocalDate submitDate;

    @OneToOne
    @JoinColumn(name = "submit_file_id", unique = true)
    private File submitFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_onepager_id", nullable = false)
    private OnePager formOnePager;

    @Column(nullable = true, length = 100)
    private String reason;

    @Builder
    public SubmitOnePager(
        Club club,
        OnePagerState onePagerState,
        LocalDate submitDate,
        File submitFile,
        OnePager formOnePager,
        String reason
    ) {
        this.club = club;
        this.onePagerState = onePagerState;
        this.submitDate = submitDate;
        this.submitFile = submitFile;
        this.formOnePager = formOnePager;
        this.reason = reason;
    }

    public void changeOnePagerState(OnePagerState onePagerState) {
        this.onePagerState = onePagerState;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSubmitFileUrl() {
        return (submitFile == null) ? null : submitFile.getFileUrl();
    }

    public String getSubmitFileName() {
        return (submitFile == null) ? null : submitFile.getFileName();
    }
}
