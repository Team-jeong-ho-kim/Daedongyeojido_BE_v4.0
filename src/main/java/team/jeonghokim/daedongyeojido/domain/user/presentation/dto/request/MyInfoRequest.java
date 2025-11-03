package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record MyInfoRequest(

        @NotBlank(message = "전화번호를 입력해주세요.")
        String phoneNumber,

        @NotBlank(message = "자기소개를 입력해주세요.")
        String introduction,

        List<Major> majors,

        List<String> links
) {
}
