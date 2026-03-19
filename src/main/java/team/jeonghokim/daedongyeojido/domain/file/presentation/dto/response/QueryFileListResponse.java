package team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response;

import java.util.List;

public record QueryFileListResponse(List<FileResponse> fileResponses) {

    public static QueryFileListResponse from(List<FileResponse> fileResponses) {
        return new QueryFileListResponse(fileResponses);
    }
}
