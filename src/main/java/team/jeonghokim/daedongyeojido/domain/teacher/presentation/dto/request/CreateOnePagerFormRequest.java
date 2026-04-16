package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record CreateOnePagerFormRequest(
        @NotBlank(message = "제목을 공백으로 둘 수 없습니다.")
        String title,

        @NotBlank(message = "첨부할 파일을 선택해주세요.")
        MultipartFile onePagerFile,

        @NotBlank(message = "원페이져 마감일을 지정해주세요.")
        LocalDateTime onePagerDuration,

        @NotBlank(message = "원페이져 설명을 기재해주세요.")
        String description
) {
}
