package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record CreateOnePagerFileFormRequest(
        @NotBlank(message = "제목을 공백으로 둘 수 없습니다.")
        @Size(max = 50, message = "제목을 50자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "담당교사이름을 비워둘 순 없습니다.")
        @Size(min = 2, max = 4, message = "담당교사 이름을 2글자 이상 4글자 이하로 입력해주세요.")
        String teacherName,

        @NotNull(message = "첨부할 파일을 선택해주세요.")
        MultipartFile formFile,

        @NotBlank(message = "원페이져 마감일을 지정해주세요.")
        String onePagerDuration,

        @NotBlank(message = "원페이져 설명을 기재해주세요.")
        @Size(max = 500, message = "설명을 500자 이하로 입력해주세요.")
        String description
) {
}
