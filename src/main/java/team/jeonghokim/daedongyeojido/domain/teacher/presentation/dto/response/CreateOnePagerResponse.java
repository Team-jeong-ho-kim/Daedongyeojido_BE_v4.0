package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response;

public record CreateOnePagerResponse(
    Long id
) {
    public static CreateOnePagerResponse of(Long id) {
        return new CreateOnePagerResponse(id);
    }
}
