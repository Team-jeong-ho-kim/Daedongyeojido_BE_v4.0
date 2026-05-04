package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto;

import java.util.List;

public record OnePagerListResponse(List<OnePagerResponse> onePagers) {

    public static OnePagerListResponse from(List<OnePagerResponse> onePagers) {
        return new OnePagerListResponse(onePagers);
    }
}
