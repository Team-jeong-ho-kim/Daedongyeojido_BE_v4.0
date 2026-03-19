package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTeacherRequest(

        @NotBlank(message = "계정 ID를 입력해주세요.")
        String accountId,

        @NotBlank(message = "이름을 입력해주세요.")
        String teacherName,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
