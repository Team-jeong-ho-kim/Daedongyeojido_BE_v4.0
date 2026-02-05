package team.jeonghokim.daedongyeojido.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyApplicantInClubException;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.repository.ScheduleRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.AlreadyInterviewScheduleExistsException;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request.InterviewScheduleRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event.UserAlarmEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.sms.event.UserSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Service
@RequiredArgsConstructor
public class DecideInterviewScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void execute(Long userId, InterviewScheduleRequest request) {

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

        executeScheduleAlarm(schedule, interviewer, applicant);

        executeScheduleSMS(interviewer.getClub(), applicant);
    }

    private void executeScheduleAlarm(Schedule schedule, User interviewer, User applicant) {

        eventPublisher.publishEvent(UserAlarmEvent.builder()
                        .title(AlarmType.INTERVIEW_SCHEDULE_CREATED.formatTitle(interviewer.getClub().getClubName()))
                        .content(AlarmType.INTERVIEW_SCHEDULE_CREATED.formatContent(
                                interviewer.getClub().getClubName(),
                                schedule.getInterviewSchedule(),
                                schedule.getInterviewTime(),
                                schedule.getPlace()))
                        .userId(applicant.getId())
                        .alarmType(AlarmType.INTERVIEW_SCHEDULE_CREATED)
                        .category(AlarmType.INTERVIEW_SCHEDULE_CREATED.getCategory())
                .build());
    }

    private void executeScheduleSMS(Club club, User applicant) {

        eventPublisher.publishEvent(UserSmsEvent.builder()
                        .clubName(club.getClubName())
                        .message(Message.INTERVIEW_SCHEDULE_DECIDED)
                        .phoneNumber(applicant.getPhoneNumber())
                .build());
    }
}
