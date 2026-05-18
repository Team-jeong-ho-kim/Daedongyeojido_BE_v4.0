package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.response;

import java.util.List;

public record TemporarySeedDevelopDataResponse(
        List<String> clubNames,
        List<String> announcementTitles
) {
}
