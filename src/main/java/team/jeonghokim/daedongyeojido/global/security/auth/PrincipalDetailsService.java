package team.jeonghokim.daedongyeojido.global.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Division;

@Component
@RequiredArgsConstructor
public class PrincipalDetailsService {

    private static final String DELIMITER = ":";
    private static final String STUDENT_PREFIX = "student";
    private static final String TEACHER_PREFIX = "teacher";

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomTeacherDetailsService customTeacherDetailsService;

    public DaedongUserDetails loadByPrincipal(String principal) {
        String[] parts = principal.split(DELIMITER, 2);

        if (parts.length < 2) {
            return (DaedongUserDetails) customUserDetailsService.loadUserByUsername(principal);
        }

        if (TEACHER_PREFIX.equals(parts[0])) {
            return customTeacherDetailsService.loadTeacherByAccountId(parts[1]);
        }

        return (DaedongUserDetails) customUserDetailsService.loadUserByUsername(parts[1]);
    }

    public String createPrincipalKey(Division division, String accountId) {
        return switch (division) {
            case TEACHER -> TEACHER_PREFIX + DELIMITER + accountId;
            case STUDENT -> STUDENT_PREFIX + DELIMITER + accountId;
        };
    }
}
