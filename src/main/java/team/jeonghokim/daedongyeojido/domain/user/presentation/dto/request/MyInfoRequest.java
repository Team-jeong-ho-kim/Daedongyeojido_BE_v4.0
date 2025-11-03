package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request;

import java.util.List;

public record MyInfoRequest(
        String phoneNumber,
        String introduction,
        List<String> majors,
        List<String> links
) {
}
