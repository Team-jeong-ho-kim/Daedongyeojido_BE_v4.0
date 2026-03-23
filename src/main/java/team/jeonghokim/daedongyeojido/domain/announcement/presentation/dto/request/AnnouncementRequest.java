package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.util.List;

public record AnnouncementRequest(

        @NotBlank(message = "공고 제목은 필수입니다.")
        @Size(max = 30, message = "공고 제목은 20자를 초과할 수 없습니다.")
        String title,

        @NotBlank(message = "소개글은 필수입니다.")
        @Size(max = 200, message = "소개글은 200자를 초과할 수 없습니다.")
        String introduction,

        @NotEmpty(message = "최소 하나 이상의 모집 전공이 필요합니다.")
        List<@NotNull(message = "모집 전공은 null일 수 없습니다.") Major> major,

        @NotNull(message = "지원 마감일은 필수입니다.")
        @FutureOrPresent(message = "지원 마감일은 과거일 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate deadline,

        @NotBlank(message = "인재상은 필수입니다.")
        @Size(max = 100, message = "인재상은 100자를 초과할 수 없습니다.")
        String talentDescription,

        @Size(max = 150, message = "과제는 150자를 초과할 수 없습니다.")
        String assignment,

        @NotBlank(message = "대표자 연락처는 필수입니다.")
        @Size(max = 11, message = "전화번호는 11자 이내로 작성해주세요.")
        String phoneNumber
) {
}
