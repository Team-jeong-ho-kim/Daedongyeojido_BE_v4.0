package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateOnePagerUrlFormRequest(
        @NotBlank(message = "제목을 공백으로 둘 수 없습니다.")
        String title,

        @NotBlank(message = "담당교사이름을 비워둘 순 없습니다.")
        String teacherName,

        @NotBlank(message = "구글 폼 링크를 첨부해주세요.")
        String formUrl,

        @NotBlank(message = "원페이져 마감일을 지정해주세요.")
        String onePagerDuration,

        @NotBlank(message = "원페이져 설명을 기재해주세요.")
        String description
) {
}
