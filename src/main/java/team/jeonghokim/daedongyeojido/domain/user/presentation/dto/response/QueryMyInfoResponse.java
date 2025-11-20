package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserLink;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;
import java.util.Optional;

@Builder
public record QueryMyInfoResponse(
        String userName,
        String classNumber,
        String introduction,
        String club,
        List<Major> major,
        List<String> link,
        String profileImage
) {
    public static QueryMyInfoResponse of(User user) {
        return new QueryMyInfoResponse(
                user.getUserName(),
                user.getClassNumber(),
                user.getIntroduction(),
                Optional.ofNullable(user.getClub()).map(Club::getClubName).orElse(null),
                user.getMajors().stream().map(UserMajor::getMajor).toList(),
                user.getLinks().stream().map(UserLink::getLink).toList(),
                user.getProfileImage()
        );
    }
}
