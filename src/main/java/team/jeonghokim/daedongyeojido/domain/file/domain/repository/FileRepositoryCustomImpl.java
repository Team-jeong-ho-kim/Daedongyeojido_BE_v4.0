package team.jeonghokim.daedongyeojido.domain.file.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response.FileResponse;
import team.jeonghokim.daedongyeojido.domain.file.presentation.dto.response.QFileResponse;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.file.domain.QFile.file;

@RequiredArgsConstructor
public class FileRepositoryCustomImpl implements FileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FileResponse> findAllFiles() {
        return queryFactory
                .select(new QFileResponse(
                        file.id,
                        file.fileName,
                        file.fileUrl
                ))
                .from(file)
                .fetch();
    }
}
