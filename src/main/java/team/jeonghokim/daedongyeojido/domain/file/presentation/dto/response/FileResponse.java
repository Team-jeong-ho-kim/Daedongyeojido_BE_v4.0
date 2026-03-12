package team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record FileResponse(

        Long clubCreationFormId,
        String fileName,
        String fileUrl
) {

    @QueryProjection
    public FileResponse(Long clubCreationFormId, String fileName, String fileUrl) {
        this.clubCreationFormId = clubCreationFormId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
