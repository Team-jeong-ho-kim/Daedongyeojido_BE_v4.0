package team.jeonghokim.daedongyeojido.infrastructure.feign.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record XquareLoginRequest(
        String accountId,
        String password
) {
}
