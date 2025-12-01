package team.jeonghokim.daedongyeojido.domain.submission.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationAnswer;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_submission")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Submission extends BaseIdEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 13)
    private ApplicationStatus applicationStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Major major;

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

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplicationAnswer> applicationAnswers = new ArrayList<>();

    @Builder
    public Submission(
            ApplicationStatus applicationStatus,
            Major major,
            String introduction,
            String userName,
            String classNumber,
            User user,
            ApplicationForm applicationForm,
            List<ApplicationAnswer> answers
    ) {
        this.applicationStatus = applicationStatus;
        this.major = major;
        this.introduction = introduction;
        this.userName = userName;
        this.classNumber = classNumber;
        this.user = user;
        this.applicationForm = applicationForm;
        addAnswers(answers);
    }

    private void addAnswers(List<ApplicationAnswer> answers) {
        answers.forEach(answer -> {
            answer.setSubmission(this);
            this.applicationAnswers.add(answer);
        });
    };

    public void update(
            Major major,
            String introduction,
            String userName,
            String classNumber,
            List<ApplicationAnswer> answers
    ) {
        this.major = major;
        this.introduction = introduction;
        this.userName = userName;
        this.classNumber = classNumber;
        this.applicationAnswers.clear();
        addAnswers(answers);
    }

    public boolean isSubmitted() {
        return applicationStatus != ApplicationStatus.WRITING;
    }

    public void submit() {
        this.applicationStatus = ApplicationStatus.SUBMITTED;
    }

    public void cancel() {
        this.applicationStatus = ApplicationStatus.WRITING;
    }

    public void applyPassResult(boolean isPassed) {
        this.applicationStatus = isPassed ? ApplicationStatus.ACCEPTED : ApplicationStatus.REJECTED;
    }
}
