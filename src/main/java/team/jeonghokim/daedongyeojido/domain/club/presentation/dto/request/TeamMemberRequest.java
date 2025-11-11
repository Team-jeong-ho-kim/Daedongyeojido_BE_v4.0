package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TeamMemberRequest {

    @NotBlank(message = "추가하실 팀원의 이름을 작성해주세요.")
    @Size(max = 4, message = "팀원 이름은 최대 4글자까지 작성할 수 있습니다.")
    private String userName;

    @NotBlank(message = "추가하실 팀원의 학번을 작성해주세요.")
    @Size(max = 4, message = "팀원 학번은 최대 4글자까지 작성할 수 있습니다.")
    private String classNumber;
}
