package team.jeonghokim.daedongyeojido.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.repository.ScheduleRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewScheduleAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewScheduleNotFoundException;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request.InterviewScheduleRequest;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.enums.SmsReferenceType;
import team.jeonghokim.daedongyeojido.domain.smshistory.service.SmsHistoryService;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event.UserAlarmEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.sms.event.UserSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Service
@RequiredArgsConstructor
public class UpdateInterviewScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserFacade userFacade;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SmsHistoryService smsHistoryService;

    @Transactional
    public void execute(Long scheduleId, InterviewScheduleRequest request) {

        User interviewer = userFacade.getCurrentUser();

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> InterviewScheduleNotFoundException.EXCEPTION);

        if (!(interviewer.getClub().getId().equals(schedule.getClub().getId()))) {
            throw InterviewScheduleAccessDeniedException.EXCEPTION;
        }

        schedule.updateSchedule(request);

        executeScheduleSMS(schedule, interviewer.getClub(), schedule.getApplicant());

        executeScheduleAlarm(schedule, interviewer, schedule.getApplicant());
    }

    private void executeScheduleSMS(Schedule schedule, Club club, User user) {
        Long smsHistoryId = smsHistoryService.createImmediate(
                SmsReferenceType.INTERVIEW_SCHEDULE,
                schedule.getId(),
                user,
                Message.INTERVIEW_SCHEDULE_CHANGED,
                club.getClubName()
        );

        applicationEventPublisher.publishEvent(UserSmsEvent.builder()
                        .smsHistoryId(smsHistoryId)
                        .clubName(club.getClubName())
                        .message(Message.INTERVIEW_SCHEDULE_CHANGED)
                        .phoneNumber(user.getPhoneNumber())
                .build());
    }

    private void executeScheduleAlarm(Schedule schedule, User interviewer, User applicant) {

        applicationEventPublisher.publishEvent(UserAlarmEvent.builder()
                .title(AlarmType.INTERVIEW_SCHEDULE_CHANGED.formatTitle(interviewer.getClub().getClubName()))
                .content(AlarmType.INTERVIEW_SCHEDULE_CHANGED.formatContent(
                        interviewer.getClub().getClubName(),
                        schedule.getInterviewSchedule(),
                        schedule.getInterviewTime(),
                        schedule.getPlace()))
                .userId(applicant.getId())
                .alarmType(AlarmType.INTERVIEW_SCHEDULE_CHANGED)
                .category(AlarmType.INTERVIEW_SCHEDULE_CHANGED.getCategory())
                .build());
    }
}
