package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UploadFileRequest(

        @NotBlank(message = "동아리 신청 양식 제목을 입력해주세요.")
        String fileName,

        @NotNull(message = "동아리 신청 양식을 첨부해주세요.")
        MultipartFile fileUrl
) {
}
