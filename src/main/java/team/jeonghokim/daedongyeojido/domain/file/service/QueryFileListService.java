package team.jeonghokim.daedongyeojido.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response.FileResponse;
import team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response.QueryFileListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryFileListService {

    private final FileRepository fileRepository;

    @Transactional(readOnly = true)
    public QueryFileListResponse execute() {
        List<FileResponse> files = fileRepository.findAllFiles();
        return QueryFileListResponse.from(files);
    }
}
