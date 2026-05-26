package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;

import java.time.LocalDateTime;
import java.util.List;

public record QueryListSubmitOnePagerResponse(
    Long id,
    String title,
    String description,
    LocalDateTime onePagerDuration,
    String fileUrl,
    String fileName,
    String formUrl,
    List<SubmitOnePagerResponse> submitOnePagers
) {
    public static QueryListSubmitOnePagerResponse of(
        OnePager onePager,
        List<SubmitOnePagerResponse> submitOnePagers
    ) {
        String fileUrl = onePager.getFormFile() != null ? onePager.getFormFileUrl() : null;
        String formUrl = onePager.getFormFile() == null ? onePager.getFormUrl() : null;

        return new QueryListSubmitOnePagerResponse(
            onePager.getId(),
            onePager.getTitle(),
            onePager.getDescription(),
            onePager.getOnePagerDuration(),
            fileUrl,
            onePager.getFormFileName(),
            formUrl,
            submitOnePagers
        );
    }
}
