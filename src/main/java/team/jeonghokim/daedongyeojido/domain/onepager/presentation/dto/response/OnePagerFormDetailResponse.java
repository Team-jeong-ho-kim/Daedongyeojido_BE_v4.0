package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;

import java.time.LocalDateTime;
import java.util.List;

public record OnePagerFormDetailResponse(
        String title,
        String description,
        String teacherName,
        OnePagerDurationType onePagerDurationType,
        LocalDateTime onePagerDuration,
        String fileUrl,
        String fileName,
        String formUrl,
        boolean submitted,
        Long submissionId,
        String myFileUrl,
        String myFileName,
        OnePagerState status,
        List<RejectedOnePagerCommentResponse> comments
) {
    public static OnePagerFormDetailResponse ofFormOnly(OnePager form) {
        return new OnePagerFormDetailResponse(
                form.getTitle(),
                form.getDescription(),
                form.getTeacher().getTeacherName(),
                form.getOnePagerDurationType(),
                resolveDuration(form),
                form.getFormFileUrl(),
                form.getFormFileName(),
                resolveFormUrl(form),
                false,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static OnePagerFormDetailResponse of(
            OnePager form,
            SubmitOnePager submission,
            List<RejectedOnePagerCommentResponse> comments
    ) {
        return new OnePagerFormDetailResponse(
                form.getTitle(),
                form.getDescription(),
                form.getTeacher().getTeacherName(),
                form.getOnePagerDurationType(),
                resolveDuration(form),
                form.getFormFileUrl(),
                form.getFormFileName(),
                resolveFormUrl(form),
                true,
                submission.getId(),
                submission.getSubmitFileUrl(),
                submission.getSubmitFileName(),
                submission.getOnePagerState(),
                comments
        );
    }

    private static LocalDateTime resolveDuration(OnePager form) {
        return form.getOnePagerDurationType() == OnePagerDurationType.DATE
                ? form.getOnePagerDuration()
                : null;
    }

    private static String resolveFormUrl(OnePager form) {
        return form.getFormFile() == null ? form.getFormUrl() : null;
    }
}
