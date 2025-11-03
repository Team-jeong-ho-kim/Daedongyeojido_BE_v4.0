package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record MyInfoRequest(

        @NotBlank
        String phoneNumber,

        @NotBlank
        String introduction,

        List<Major> majors,
        List<String> links
) {
}
