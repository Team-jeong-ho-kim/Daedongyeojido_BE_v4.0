package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;

@Service
@RequiredArgsConstructor
public class CreateRejectedOnePagerCommentService {
    private final OnePagerRepository onePagerRepository;


}
