package team.jeonghokim.daedongyeojido.domain.clubcreationform.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.domain.ClubCreationForm;

import java.util.Optional;

public interface ClubCreationFormRepository extends JpaRepository<ClubCreationForm, Long> {

}
