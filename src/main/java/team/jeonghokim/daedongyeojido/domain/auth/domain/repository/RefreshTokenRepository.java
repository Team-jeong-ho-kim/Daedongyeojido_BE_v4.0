package team.jeonghokim.daedongyeojido.domain.auth.domain.repository;

import org.springframework.data.repository.CrudRepository;
import team.jeonghokim.daedongyeojido.domain.auth.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByAccountId(String accountId);
}
