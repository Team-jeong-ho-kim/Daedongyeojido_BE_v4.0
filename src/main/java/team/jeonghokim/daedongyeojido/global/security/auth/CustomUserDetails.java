package team.jeonghokim.daedongyeojido.global.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public record CustomUserDetails(User user) implements UserDetails {
    @Override
    public String getUsername() {
        return user.getAccountId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    //사용자의 권한 목록을 반환
        return new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
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

}
