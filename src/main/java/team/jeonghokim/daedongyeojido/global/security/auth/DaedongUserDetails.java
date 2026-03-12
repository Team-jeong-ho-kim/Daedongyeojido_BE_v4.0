package team.jeonghokim.daedongyeojido.global.security.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface DaedongUserDetails extends UserDetails {

    String getPrincipalKey();

    String getAccountId();
}
