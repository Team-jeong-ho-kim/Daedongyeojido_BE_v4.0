package team.jeonghokim.daedongyeojido.domain.onepager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_rejected_onepager_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RejectedOnePagerComment extends BaseIdEntity {
    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_onepager_id", nullable = false)
    private SubmitOnePager onePager;

    @Column(nullable = false, length = 4)
    private String commentWriter;

    @Builder
    public RejectedOnePagerComment(
        String comment,
        SubmitOnePager onePager,
        String commentWriter
    ) {
        this.comment = comment;
        this.onePager = onePager;
        this.commentWriter = commentWriter;
    }
}
