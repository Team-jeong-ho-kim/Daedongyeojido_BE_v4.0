package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import java.util.List;

public record QueryApplicationFormListResponse(List<ApplicationFormResponse> applicationForms) {

    public static QueryApplicationFormListResponse from(List<ApplicationFormResponse> applicationForms) {
        return new QueryApplicationFormListResponse(applicationForms);
    }
}
