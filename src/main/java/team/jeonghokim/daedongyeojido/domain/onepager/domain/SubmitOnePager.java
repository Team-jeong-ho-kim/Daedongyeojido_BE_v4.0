package team.jeonghokim.daedongyeojido.domain.onepager.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_submit_onepager")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubmitOnePager extends BaseIdEntity {
    @Column(nullable = false)
    private String clubName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OnePagerState onePagerState;

    @Column(nullable = false)
    private LocalDate submitDate;

    @OneToOne
    @JoinColumn(name = "submit_file_id", unique = true)
    private File submitFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_onepager_id", nullable = false)
    private OnePager formOnePager;

    @Builder
    public SubmitOnePager(
        String clubName,
        OnePagerState onePagerState,
        LocalDate submitDate,
        File submitFile,
        OnePager formOnePager
    ) {
        this.clubName = clubName;
        this.onePagerState = onePagerState;
        this.submitDate = submitDate;
        this.submitFile = submitFile;
        this.formOnePager = formOnePager;
    }
}
