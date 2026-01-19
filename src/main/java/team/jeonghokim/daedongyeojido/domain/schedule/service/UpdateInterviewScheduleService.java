package team.jeonghokim.daedongyeojido.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.repository.ScheduleRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewScheduleAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewScheduleNotFoundException;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request.InterviewScheduleRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.event.sms.event.UserSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Service
@RequiredArgsConstructor
public class UpdateInterviewScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserFacade userFacade;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void execute(Long scheduleId, InterviewScheduleRequest request) {

        User interviewer = userFacade.getCurrentUser();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> InterviewScheduleNotFoundException.EXCEPTION);

        if (!(interviewer.getClub().getId().equals(schedule.getClub().getId()))) {
            throw InterviewScheduleAccessDeniedException.EXCEPTION;
        }

        schedule.updateSchedule(request);

        executeScheduleSMS(interviewer.getClub(), schedule.getApplicant());
    }

    private void executeScheduleSMS(Club club, User user) {

        applicationEventPublisher.publishEvent(UserSmsEvent.builder()
                        .clubName(club.getClubName())
                        .message(Message.INTERVIEW_SCHEDULE_CHANGED)
                        .phoneNumber(user.getPhoneNumber())
                .build());
    }
}
