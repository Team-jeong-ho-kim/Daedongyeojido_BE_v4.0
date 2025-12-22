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
        String clubName,
        List<Major> major,
        List<String> link,
        String profileImage
) {

    public static QueryMyInfoResponse from(User user) {
        return QueryMyInfoResponse.builder()
                .userName(user.getUserName())
                .classNumber(user.getClassNumber())
                .introduction(user.getIntroduction())
                .clubName(Optional.ofNullable(user.getClub()).map(Club::getClubName).orElse(null))
                .major(user.getMajors().stream().map(UserMajor::getMajor).toList())
                .link(user.getLinks().stream().map(UserLink::getLink).toList())
                .profileImage(user.getProfileImage())
                .build();
    }
}
