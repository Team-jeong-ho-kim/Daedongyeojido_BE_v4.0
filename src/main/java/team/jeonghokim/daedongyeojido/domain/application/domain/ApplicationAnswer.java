package team.jeonghokim.daedongyeojido.domain.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_application_answer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAnswer extends BaseIdEntity {

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_question_id", nullable = false)
    private ApplicationQuestion applicationQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Submission submission;
}
