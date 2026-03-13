package team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record FileResponse(

        Long fileId,
        String fileName,
        String fileUrl
) {

    @QueryProjection
    public FileResponse(Long fileId, String fileName, String fileUrl) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
