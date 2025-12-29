package team.jeonghokim.daedongyeojido.infrastructure.event.listner.alarm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.ClubAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.ClubAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.club.ClubAlarmEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.exception.AlarmEventFinalFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.event.exception.HttpApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubAlarmEventListener {
    private final ClubAlarmRepository clubAlarmRepository;
    private final ClubRepository clubRepository;

    private static final String CLUB_EVENT_RETRY = "recoverClubEvent";

    @Async
    @Retryable(
            retryFor = HttpApiException.class,
            recover = CLUB_EVENT_RETRY,
            backoff = @Backoff(delay = 500, multiplier = 2)
    )
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleClubAlarmEvent(ClubAlarmEvent event) {
        try {
            Club club = clubRepository.findById(event.clubId())
                    .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

            ClubAlarm alarm = clubAlarmRepository.save(ClubAlarm.builder()
                    .title(event.title())
                    .content(event.content())
                    .club(club)
                    .alarmType(event.alarmType())
                    .build());

        } catch (Exception e) {
            log.warn("동아리 알림 전송 실패 재시도 예정 (clubId={}, alarmType={})", event.clubId(), event.alarmType(), e);
            throw new HttpApiException(e);
        }
    }

    @Recover
    public void recoverClubEvent(HttpApiException e, ClubAlarmEvent event) {
        log.error("동아리 알람 이벤트 최종 실패: clubId={} alarmType={}",
                event.clubId(), event.alarmType(), e);

        throw new AlarmEventFinalFailedException();
    }
}
