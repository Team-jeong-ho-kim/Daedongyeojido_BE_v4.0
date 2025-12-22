package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationListResponse;

import java.util.List;

@Builder
public record QueryApplicationListResponse(List<ApplicationListResponse> applications) {

    public static QueryApplicationListResponse from(List<ApplicationListResponse> applications) {
        return QueryApplicationListResponse.builder()
                .applications(applications)
                .build();
    }
}
