package team.jeonghokim.daedongyeojido.domain.file.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.UploadFileRequest;
import team.jeonghokim.daedongyeojido.domain.file.service.DeleteFileService;
import team.jeonghokim.daedongyeojido.domain.file.service.UploadFileService;
import team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response.QueryFileListResponse;
import team.jeonghokim.daedongyeojido.domain.file.service.QueryFileListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final UploadFileService uploadFileService;
    private final QueryFileListService queryFileListService;
    private final DeleteFileService deleteFileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadFile(@ModelAttribute @Valid UploadFileRequest request) {
        uploadFileService.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryFileListResponse queryClubCreationForm() {
        return queryFileListService.execute();
    }

    @DeleteMapping("/{file-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(@PathVariable("file-id") Long fileId) {
        deleteFileService.execute(fileId);
    }
}
