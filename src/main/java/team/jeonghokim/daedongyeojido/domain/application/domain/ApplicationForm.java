package team.jeonghokim.daedongyeojido.domain.application.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_application_form")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationForm extends BaseIdEntity {

    @Column(nullable = false, length = 30)
    private String applicationFormTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @OneToMany(mappedBy = "applicationForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplicationQuestion> applicationQuestions = new ArrayList<>();;

    @Column(nullable = false)
    private LocalDate submissionDuration;

    @Builder
    public ApplicationForm(String applicationFormTitle, User user, Club club, List<ApplicationQuestion> applicationQuestions, LocalDate submissionDuration) {
        this.applicationFormTitle = applicationFormTitle;
        this.user = user;
        this.club = club;
        addApplicationQuestion(applicationQuestions);
        this.submissionDuration = submissionDuration;
    }

    private void addApplicationQuestion(List<ApplicationQuestion> applicationQuestions) {
        applicationQuestions.forEach(applicationQuestion -> {
            applicationQuestion.setApplicationForm(this);
            this.applicationQuestions.add(applicationQuestion);
        });
    }
}
