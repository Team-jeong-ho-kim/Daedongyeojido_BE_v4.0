package team.jeonghokim.daedongyeojido.domain.onepager.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OnePagerDurationType {
    DATE("유기한"),
    INFINITY("무기한");

    @JsonValue
    private final String description;

    OnePagerDurationType(String description) {
        this.description = description;
    }
}
