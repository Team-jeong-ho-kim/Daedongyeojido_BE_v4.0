package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DecideTeamMemberApplicationRequest {
    @NotNull(message = "팀원 신청 수락 및 거절 여부는 null일 수 없습니다.")
    Boolean isApproved;
}
