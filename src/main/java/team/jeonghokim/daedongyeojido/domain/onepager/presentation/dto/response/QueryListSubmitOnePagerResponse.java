package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;

import java.time.LocalDateTime;
import java.util.List;

public record QueryListSubmitOnePagerResponse(
    String title,
    String description,
    LocalDateTime onePagerDuration,
    String fileUrl,
    List<SubmitOnePagerResponse> submitOnePagers

) {
    public static QueryListSubmitOnePagerResponse from(
        OnePager onePager,
        String fileUrl,
        List<SubmitOnePagerResponse> submitOnePagers
    ) {
        return new QueryListSubmitOnePagerResponse(
            onePager.getTitle(),
            onePager.getDescription(),
            onePager.getOnePagerDuration(),
            fileUrl,
            submitOnePagers
        );
    }
}
