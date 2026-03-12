package team.jeonghokim.daedongyeojido.domain.file.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response.QueryFileListResponse;
import team.jeonghokim.daedongyeojido.domain.file.service.QueryFileListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final QueryFileListService queryFileListService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryFileListResponse queryClubCreationForm() {
        return queryFileListService.execute();
    }
}
