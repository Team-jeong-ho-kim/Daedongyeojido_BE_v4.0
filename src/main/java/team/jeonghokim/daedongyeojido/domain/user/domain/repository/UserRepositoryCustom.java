package team.jeonghokim.daedongyeojido.domain.user.domain.repository;

import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.MyInfoResponse;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<MyInfoResponse> findByUserId(Long userId);
}
