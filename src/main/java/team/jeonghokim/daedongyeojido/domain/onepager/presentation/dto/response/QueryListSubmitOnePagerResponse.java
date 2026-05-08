package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

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
        String title,
        String description,
        LocalDateTime onePagerDuration,
        String fileUrl,
        List<SubmitOnePagerResponse> submitOnePagers
    ) {
        return new QueryListSubmitOnePagerResponse(
            title,
            description,
            onePagerDuration,
            fileUrl,
            submitOnePagers
        );
    }
}
