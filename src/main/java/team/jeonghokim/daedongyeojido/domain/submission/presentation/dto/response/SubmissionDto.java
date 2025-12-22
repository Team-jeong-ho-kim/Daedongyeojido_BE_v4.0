package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationAnswer;

@Builder
public record SubmissionDto(
        Long questionId,
        String content
) {

    public static SubmissionDto from(ApplicationAnswer applicationAnswer) {
        return SubmissionDto.builder()
                .questionId(applicationAnswer.getApplicationQuestion().getId())
                .content(applicationAnswer.getContent())
                .build();
    }
}
