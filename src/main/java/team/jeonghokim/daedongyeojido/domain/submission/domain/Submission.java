package team.jeonghokim.daedongyeojido.domain.submission.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_submission")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Submission extends BaseIdEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 13)
    private ApplicationStatus applicationStatus;

    @Column(nullable = false)
    private LocalDate submissionDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private List<Major> major;

    @Column(length = 300)
    private String introduction;

    @Column(nullable = false, length = 4)
    private String userName;

    @Column(nullable = false, length = 4)
    private String classNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_form_id", nullable = false)
    private ApplicationForm applicationForm;
}
