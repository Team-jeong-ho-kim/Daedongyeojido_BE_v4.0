package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DecideTeamMemberApplicationRequest {
    @NotNull(message = "팀원 신청 수락 및 거절 여부는 null일 수 없습니다.")
    Boolean isApproved;

    @NotNull(message = "알람 ID를 입력해주세요.")
    Long alarmId;

    @NotNull(message = "실행 여부를 입력해주세요.")
    boolean isExecuted;
}
