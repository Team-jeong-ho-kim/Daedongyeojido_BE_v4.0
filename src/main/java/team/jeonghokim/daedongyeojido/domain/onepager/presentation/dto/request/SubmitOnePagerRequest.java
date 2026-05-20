package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record SubmitOnePagerRequest(
    @NotNull(message = "파일을 첨부해주세요.")
    MultipartFile submitFile
) {
}
