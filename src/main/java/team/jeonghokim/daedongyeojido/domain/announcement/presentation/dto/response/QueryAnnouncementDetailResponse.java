package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.AnnouncementMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.util.List;

@Builder
public record QueryAnnouncementDetailResponse(
        String title,
        List<Major> major,
        LocalDate deadline,
        String introduction,
        String talentDescription,
        String assignment
) {

    public static QueryAnnouncementDetailResponse from(Announcement announcement) {
        return QueryAnnouncementDetailResponse.builder()
                .title(announcement.getTitle())
                .major(announcement.getAnnouncementMajors().stream().map(AnnouncementMajor::getMajor).toList())
                .deadline(announcement.getDeadline())
                .introduction(announcement.getIntroduction())
                .talentDescription(announcement.getTalentDescription())
                .assignment(announcement.getAssignment())
                .build();
    }
}
