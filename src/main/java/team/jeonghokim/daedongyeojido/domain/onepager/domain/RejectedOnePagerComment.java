package team.jeonghokim.daedongyeojido.domain.onepager.domain;

import jakarta.persistence.*;
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
    @JoinColumn(name = "onepager_id", nullable = false)
    private OnePager onePager;

    @Column(nullable = false, length = 4)
    private String commentWriter;

    @Builder
    public RejectedOnePagerComment(
        String comment,
        OnePager onePager,
        String commentWriter
    ) {
        this.comment = comment;
        this.onePager = onePager;
        this.commentWriter = commentWriter;
    }
}
