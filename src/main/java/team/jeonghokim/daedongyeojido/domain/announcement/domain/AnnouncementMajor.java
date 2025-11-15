package team.jeonghokim.daedongyeojido.domain.announcement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_announcement_major")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementMajor extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Major major;

    @Builder
    public AnnouncementMajor(Major major) {
        this.major = major;
    }

    protected void setAnnouncement(Announcement announcement) {
        if (this.announcement != null) {
            return;
        }
        this.announcement = announcement;
    }
}
