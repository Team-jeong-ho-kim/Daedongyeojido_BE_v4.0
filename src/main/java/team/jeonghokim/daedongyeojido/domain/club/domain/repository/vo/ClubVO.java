package team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClubVO {

    private final Long clubId;
    private final String clubName;
    private final String clubImage;
    private final String introduction;
    private final List<Major> majors;
}
