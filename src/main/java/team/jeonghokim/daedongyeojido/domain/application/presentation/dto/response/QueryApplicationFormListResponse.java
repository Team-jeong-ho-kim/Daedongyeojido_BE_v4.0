package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryApplicationFormListResponse(List<ApplicationFormResponse> listResponses) {
}
