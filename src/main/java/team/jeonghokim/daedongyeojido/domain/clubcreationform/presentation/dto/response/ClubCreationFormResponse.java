package team.jeonghokim.daedongyeojido.domain.clubcreationform.presentation.dto.response;

import lombok.Builder;

@Builder
public record ClubCreationFormResponse(

        String fileName,
        String fileUrl
) {
    public static ClubCreationFormResponse of(String fileName, String fileUrl) {
        return ClubCreationFormResponse.builder()
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
    }
}
