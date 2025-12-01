package team.jeonghokim.daedongyeojido.infrastructure.feign.xquare.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record XquareResponse(
        UUID id,
        String accountId,
        String password,
        String name,
        Integer grade,
        Integer classNum,
        Integer num,
        String userRole,
        LocalDate birth_day,
        String profileImgUrl,
        String clubName
) {
}
