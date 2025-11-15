package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.util.List;

public record CreateAnnouncementRequest(

        @NotBlank(message = "공고 제목은 필수입니다.")
        @Size(max = 20, message = "공고 제목은 20자를 초과할 수 없습니다.")
        String title,

        @NotBlank(message = "소개글은 필수입니다.")
        @Size(max = 200, message = "소개글은 200자를 초과할 수 없습니다.")
        String introduction,

        @NotNull(message = "모집 전공은 비어 있을 수 없습니다.")
        List<Major> major,

        @NotNull(message = "지원 마감일은 필수입니다.")
        LocalDate deadline,

        @NotBlank(message = "인재상은 필수입니다.")
        @Size(max = 100, message = "인재상은 100자를 초과할 수 없습니다.")
        String talentDescription,

        @Size(max = 150, message = "과제는 150자를 초과할 수 없습니다.")
        String assignment
) {
}
