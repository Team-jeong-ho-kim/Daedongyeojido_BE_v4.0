package team.jeonghokim.daedongyeojido.domain.file.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long>, FileRepositoryCustom {

    Optional<File> findByFileName(String fileName);

}
