package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

public record ClubMembersDto(
        String userName,
        List<Major> majors,
        String introduce
) {

    public static ClubMembersDto from(User user) {
        return new ClubMembersDto(
                user.getUserName(),
                user.getMajors().stream().map(UserMajor::getMajor).toList(),
                user.getIntroduction()
        );
    }
}
