package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record UpdateClubRequest(

        @Size(max = 20, message = "동아리 이름은 최대 20자까지 작성할 수 있습니다.")
        String clubName,

        MultipartFile clubImage,

        @Size(max = 30, message = "한 줄 소개는 최대 30자까지 작성할 수 있습니다.")
        String oneLiner,

        @Size(max = 500, message = "동아리 소개는 최대 500자까지 작성할 수 있습니다.")
        String introduction,

        List<Major> major,

        List<@URL(message = "유효한 URL 형식이어야 합니다.") String> link
) { }
