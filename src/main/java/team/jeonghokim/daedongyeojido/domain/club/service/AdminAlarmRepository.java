package team.jeonghokim.daedongyeojido.domain.club.service;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.AdminAlarm;

interface AdminAlarmRepository extends JpaRepository<AdminAlarm, Long> {
}
