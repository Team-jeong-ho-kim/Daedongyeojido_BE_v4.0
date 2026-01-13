package team.jeonghokim.daedongyeojido.domain.admin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.admin.domain.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAccountId(String accountId);
}
