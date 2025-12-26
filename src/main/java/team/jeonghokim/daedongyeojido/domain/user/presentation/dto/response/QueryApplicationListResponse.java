package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationListResponse;

import java.util.List;

public record QueryApplicationListResponse(List<ApplicationListResponse> applications) {

    public static QueryApplicationListResponse from(List<ApplicationListResponse> applications) {
        return new QueryApplicationListResponse(applications);
    }
}
