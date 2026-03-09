package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import lombok.Builder;

@Builder
public record ClubCreationInformationResponse(

        ClubDetailDto club,
        String userName,
        String classNumber,
        String clubCreationForm
) {

    public static ClubCreationInformationResponse of(ClubDetailDto clubDetailDto, String userName, String classNumber, String clubCreationForm) {
        return ClubCreationInformationResponse.builder()
                .club(clubDetailDto)
                .userName(userName)
                .classNumber(classNumber)
                .clubCreationForm(clubCreationForm)
                .build();
    }
}
