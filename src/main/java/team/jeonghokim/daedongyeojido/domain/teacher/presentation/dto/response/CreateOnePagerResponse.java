package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response;

public record CreateOnePagerResponse(
    Long onePagerId
) {
    public static CreateOnePagerResponse of(Long onePagerId) {
        return new CreateOnePagerResponse(onePagerId);
    }
}
