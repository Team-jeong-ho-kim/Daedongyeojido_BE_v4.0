package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;

public record SubmitOnePagerRequest(
    @NotNull(message = "파일을 첨부해주세요.")
    File submitFile
) {
}
