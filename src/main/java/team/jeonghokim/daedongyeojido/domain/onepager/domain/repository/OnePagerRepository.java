package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;

import java.util.List;

public interface OnePagerRepository extends JpaRepository<OnePager, Long> {
    List<OnePager> findAllByOrderByIdDesc();

    long countByFormFileAndIdNot(File formFile, Long onePagerId);
}
