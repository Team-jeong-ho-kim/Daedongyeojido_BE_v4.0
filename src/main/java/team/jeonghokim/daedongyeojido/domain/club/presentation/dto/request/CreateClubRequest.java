package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

@Getter
public class CreateClubRequest {
    @NotBlank(message = "동아리 이름은 필수입니다.")
    @Size(max = 20, message = "동아리 이름은 최대 20자까지 작성할 수 있습니다.")
    private String clubName;

    @NotBlank(message = "동아리 이미지는 필수입니다.")
    @Size(max = 200, message = "이미지 URL은 최대 200자까지 작성할 수 있습니다.")
    private String clubImage;

    @NotBlank(message = "한 줄 소개는 필수입니다.")
    @Size(max = 30, message = "한 줄 소개는 최대 30자까지 작성할 수 있습니다.")
    private String oneLiner;

    @NotBlank(message = "동아리 소개는 필수입니다.")
    @Size(max = 500, message = "동아리 소개는 최대 500자까지 작성할 수 있습니다.")
    private String introduction;

    @NotNull(message = "전공 리스트는 필수입니다.")
    private List<Major> major;

    private List<@URL(message = "유효한 URL 형식이어야 합니다.") String> link;
}
