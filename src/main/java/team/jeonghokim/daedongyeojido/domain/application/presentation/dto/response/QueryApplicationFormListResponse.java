package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryApplicationFormListResponse(List<ApplicationFormResponse> applicationForms) {

    public static QueryApplicationFormListResponse from(List<ApplicationFormResponse> applicationForms) {
        return QueryApplicationFormListResponse.builder()
                .applicationForms(applicationForms)
                .build();
    }
}
