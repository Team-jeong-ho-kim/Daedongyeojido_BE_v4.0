package team.jeonghokim.daedongyeojido.global.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;

import java.util.Collection;
import java.util.List;

public record CustomTeacherDetails(Teacher teacher) implements DaedongUserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_TEACHER"));
    }

    @Override
    public String getPassword() {
        return teacher.getPassword();
    }

    @Override
    public String getUsername() {
        return teacher.getAccountId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPrincipalKey() {
        return "teacher:" + teacher.getAccountId();
    }

    @Override
    public String getAccountId() {
        return teacher.getAccountId();
    }
}
