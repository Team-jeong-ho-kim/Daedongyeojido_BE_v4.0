package team.jeonghokim.daedongyeojido.domain.application.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_application_answer")
@Getter
@NoArgsConstructor
public class ApplicationAnswer extends BaseIdEntity {

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_question_id", nullable = false)
    private ApplicationQuestion applicationQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @Builder
    public ApplicationAnswer(String content, ApplicationQuestion applicationQuestion) {
        this.content = content;
        this.applicationQuestion = applicationQuestion;
    }

    public void setSubmission(Submission submission) {
        if (this.submission != null) {
            return;
        }
        this.submission = submission;
    }
}
