package team.jeonghokim.daedongyeojido.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyApplicantInClubException;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.repository.ScheduleRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.AlreadyInterviewScheduleExistsException;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request.DecideInterviewScheduleRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class DecideInterviewScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long userId, DecideInterviewScheduleRequest request) {
        User interviewer = userFacade.getCurrentUser();
        User applicant = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (scheduleRepository.existsByApplicantAndClub(applicant, interviewer.getClub())) {
            throw AlreadyInterviewScheduleExistsException.EXCEPTION;
        }

        if (applicant.getClub() != null) {
            throw AlreadyApplicantInClubException.EXCEPTION;
        }

        Schedule schedule = Schedule.builder()
                .interviewSchedule(request.interviewSchedule())
                .place(request.place())
                .interviewTime(request.interviewTime())
                .applicant(applicant)
                .club(interviewer.getClub())
                .build();

        scheduleRepository.save(schedule);
    }
}
