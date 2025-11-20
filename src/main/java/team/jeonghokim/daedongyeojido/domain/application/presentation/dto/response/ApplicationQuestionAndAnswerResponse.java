package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationAnswer;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;

import java.util.List;
import java.util.Optional;

public record ApplicationQuestionAndAnswerResponse(
        Long applicationQuestionId,
        String question,
        Long applicationAnswerId,
        String answer
) {
    public static List<ApplicationQuestionAndAnswerResponse> from(Submission submission) {
        List<ApplicationQuestion> questions = submission.getApplicationForm().getApplicationQuestions();
        List<ApplicationAnswer> answers = submission.getApplicationAnswers();

        return questions.stream()
                .map(question -> {
                    ApplicationAnswer applicationAnswer = answers.stream()
                            .filter(a -> a.getApplicationQuestion().getId().equals(question.getId()))
                            .findFirst()
                            .orElse(null);

                    Long applicationAnswerId = Optional.ofNullable(applicationAnswer)
                            .map(ApplicationAnswer::getId)
                            .orElse(null);

                    String answer = Optional.ofNullable(applicationAnswer)
                            .map(ApplicationAnswer::getContent)
                            .orElse(null);

                    return new ApplicationQuestionAndAnswerResponse(
                            question.getId(),
                            question.getContent(),
                            applicationAnswerId,
                            answer
                    );
                })
                .toList();
    }
}
