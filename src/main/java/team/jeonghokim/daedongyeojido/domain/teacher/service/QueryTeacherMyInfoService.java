package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.facade.TeacherFacade;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherMyInfoResponse;

@Service
@RequiredArgsConstructor
public class QueryTeacherMyInfoService {

    private final TeacherFacade teacherFacade;
    private final ClubRepository clubRepository;

    @Transactional(readOnly = true)
    public QueryTeacherMyInfoResponse execute() {
        String accountId = teacherFacade.getCurrentTeacher().getAccountId();
        Club club = clubRepository.findByTeacherAccountId(accountId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        return QueryTeacherMyInfoResponse.from(club);
    }
}
