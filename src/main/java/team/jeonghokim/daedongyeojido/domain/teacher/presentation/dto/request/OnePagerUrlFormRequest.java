package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;

import java.time.LocalDateTime;

public record OnePagerUrlFormRequest(
        @NotBlank(message = "제목을 공백으로 둘 수 없습니다.")
        @Size(max = 50, message = "제목을 50자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "구글 폼 링크를 첨부해주세요.")
        String formUrl,

        @NotNull(message = "양식 마감 날짜를 설정해주세요.")
        OnePagerDurationType onePagerDurationType,

        LocalDateTime onePagerDuration,

        @NotBlank(message = "원페이져 설명을 기재해주세요.")
        @Size(max = 500, message = "설명을 500자 이하로 입력해주세요.")
        String description
) {
}
