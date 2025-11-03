package team.jeonghokim.daedongyeojido.domain.user.mapper;

import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserLink;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserMajor;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.MyInfoRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public List<UserMajor> toUserMajors(MyInfoRequest request, User user) {
        return request.majors().stream()
                .map(major -> UserMajor.builder()
                        .user(user)
                        .major(major)
                        .build())
                .collect(Collectors.toList());
    }

    public List<UserLink> toUserLinks(MyInfoRequest request, User user) {
        return Optional.ofNullable(request.links())
                .orElseGet(List::of)
                .stream()
                .map(link -> UserLink.builder()
                        .user(user)
                        .link(link)
                        .build())
                .collect(Collectors.toList());
    }
}
