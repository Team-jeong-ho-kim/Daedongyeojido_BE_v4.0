package team.jeonghokim.daedongyeojido.domain.file.domain.repository;

import team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response.FileResponse;

import java.util.List;

public interface FileRepositoryCustom {

    List<FileResponse> findAllFiles();
}
