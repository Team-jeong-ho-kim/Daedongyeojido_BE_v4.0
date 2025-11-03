package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record MyInfoRequest(

        @NotBlank
        @Size(max = 11, message = "전화번호는 11자 이내로 작성해주세요.")
        String phoneNumber,

        @NotBlank
        @Size(max = 30, message = "자기소개는 30자 이내로 작성해주세요.")
        String introduction,

        List<Major> majors,

        List<@URL(message = "유효한 URL 형식이어야 합니다")String> links
        ) {
}
