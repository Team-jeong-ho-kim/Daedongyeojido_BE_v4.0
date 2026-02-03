package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.AnnouncementMajor;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.enums.Status;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.util.List;

@Builder
public record QueryAnnouncementDetailResponse(
        Long clubId,
        String title,
        List<Major> major,
        String phoneNumber,
        LocalDate deadline,
        String introduction,
        String talentDescription,
        String assignment,
        Status status
) {

    public static QueryAnnouncementDetailResponse from(Announcement announcement) {
        return QueryAnnouncementDetailResponse.builder()
                .clubId(announcement.getClub().getId())
                .title(announcement.getTitle())
                .major(announcement.getAnnouncementMajors().stream().map(AnnouncementMajor::getMajor).toList())
                .phoneNumber(announcement.getPhoneNumber())
                .deadline(announcement.getDeadline())
                .introduction(announcement.getIntroduction())
                .talentDescription(announcement.getTalentDescription())
                .assignment(announcement.getAssignment())
                .status(announcement.getStatus())
                .build();
    }
}
