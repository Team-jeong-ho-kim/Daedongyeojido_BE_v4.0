package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;

import java.time.LocalDate;

public record SubmitOnePagerRequest(
    @NotNull(message = "파일을 첨부해주세요.")
    File submitFile,

    @NotNull(message = "제출기한을 비워둘 순 없습니다.")
    LocalDate submitDate
) {
}
