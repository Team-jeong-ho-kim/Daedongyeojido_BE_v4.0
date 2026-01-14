package team.jeonghokim.daedongyeojido.infrastructure.feign.xquare.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record XquareResponse(
        UUID id,

        @JsonProperty("account_id")
        String accountId,

        String password,

        String name,

        Integer grade,

        @JsonProperty("class_num")
        Integer classNum,

        Integer num,

        @JsonProperty("user_role")
        String userRole,

        @JsonProperty("birth_day")
        LocalDate birthDay,

        String profileImgUrl,

        String clubName
) {
}
 