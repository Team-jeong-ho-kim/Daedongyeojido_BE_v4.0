package team.jeonghokim.daedongyeojido.domain.application.domain.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    NOT_SUBMITTED,
    SUBMITTED,
    WRITING,
    ACCEPTED,
    REJECTED;
}
