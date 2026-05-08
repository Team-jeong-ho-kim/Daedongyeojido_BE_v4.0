package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;

import java.time.LocalDateTime;

public record OnePagerFileFormRequest(
        @NotBlank(message = "제목을 공백으로 둘 수 없습니다.")
        @Size(max = 50, message = "제목을 50자 이하로 입력해주세요.")
        String title,

        @NotNull(message = "첨부할 파일을 선택해주세요.")
        MultipartFile formFile,

        @NotNull(message = "양식 마감 날짜를 설정해주세요.")
        OnePagerDurationType onePagerDurationType,

        LocalDateTime onePagerDuration,

        @NotBlank(message = "원페이져 설명을 기재해주세요.")
        @Size(max = 500, message = "설명을 500자 이하로 입력해주세요.")
        String description
) {
}
