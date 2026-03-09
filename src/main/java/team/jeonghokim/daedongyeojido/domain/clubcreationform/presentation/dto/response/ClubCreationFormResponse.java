package team.jeonghokim.daedongyeojido.domain.clubcreationform.presentation.dto.response;

import lombok.Builder;

@Builder
public record ClubCreationFormResponse(

        Long clubCreationFormId,
        String fileName,
        String fileUrl
) {
    public static ClubCreationFormResponse of(Long clubCreationFormId, String fileName, String fileUrl) {
        return ClubCreationFormResponse.builder()
                .clubCreationFormId(clubCreationFormId)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
    }
}
