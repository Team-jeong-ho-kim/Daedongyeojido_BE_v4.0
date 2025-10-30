package team.jeonghokim.daedongyeojido.global.xquare.dto;

import java.util.UUID;

public record XquareResponse(
        UUID id,
        String accountId,
        String password,
        String name,
        Integer classNum
) {
}
