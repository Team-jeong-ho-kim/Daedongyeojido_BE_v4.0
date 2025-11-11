package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

@Builder
public record MyInfoResponse(
        String userName,
        String classNumber,
        String introduction,
        String club,
        List<Major> major,
        List<String> link,
        String profileImage
) {
}
