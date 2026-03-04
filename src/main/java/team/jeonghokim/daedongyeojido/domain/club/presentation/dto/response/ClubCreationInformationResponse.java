package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import lombok.Builder;

@Builder
public record ClubCreationInformationResponse(

        ClubDetailDto club,
        String userName,
        String classNumber
) {
}
