package team.jeonghokim.daedongyeojido.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import team.jeonghokim.daedongyeojido.domain.auth.domain.RefreshToken;
import team.jeonghokim.daedongyeojido.domain.auth.domain.repository.RefreshTokenRepository;
import team.jeonghokim.daedongyeojido.domain.auth.exception.RefreshTokenNotFoundException;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.global.security.auth.CustomUserDetailsService;
import team.jeonghokim.daedongyeojido.global.security.exception.ExpiredTokenException;
import team.jeonghokim.daedongyeojido.global.security.exception.InvalidTokenException;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String CLAIM_TYPE = "typ";
    private static final String ACCESS_TYPE = "access";
    private static final String REFRESH_TYPE = "refresh";
    private static final int MILLISECONDS = 1000;

    //access token 생성
    public String createAccessToken(String accountId) {
        Date now = new Date(); //코드를 실행한 시점의 현재 날짜와 시간이 저장(일시적)

        return Jwts.builder()
                .setSubject(accountId) //토큰의 소유자
                .claim(CLAIM_TYPE, ACCESS_TYPE) //액세스 토큰임을 나타냄
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessExpiration() * MILLISECONDS)) //토큰의 만료 시간 설정
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey()) //HS512 알고리즘, 비밀 키를 Jwtproperties에서 가져옴
                .compact();

    }

    //refresh token 생성
    public String createRefreshToken(String accountId) {
        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setSubject(accountId)
                .claim(CLAIM_TYPE, REFRESH_TYPE)  //refresh 토큰임을 나타냄
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshExpiration() * MILLISECONDS))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey()) //
                .compact();

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .accountId(accountId)
                        .refreshToken(refreshToken)
                        .timeToLive((jwtProperties.getRefreshExpiration()))
                        .build()
        );

        return refreshToken;
    }

    // 토큰에 담겨 있는 userId로 SpringSecurity Authentication 정보를 반환 하는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims getClaims(String token) {
        try {
            return Jwts
                    .parser() //JWT parser 생성
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        }
        catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    public TokenResponse receiveToken(String accountId) {

        return TokenResponse
                .builder()
                .accessToken(createAccessToken(accountId))
                .refreshToken(createRefreshToken(accountId))
                .build();
    }

    public TokenResponse reissueToken(String refreshToken) {
        if (isNotRefreshToken(refreshToken)) {
            throw InvalidTokenException.EXCEPTION;
        }

        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .map(token -> {
                    String accountId = token.getAccountId();
                    TokenResponse tokenResponse = receiveToken(accountId);

                    token.update(tokenResponse.refreshToken(), jwtProperties.getRefreshExpiration());
                    return new TokenResponse(tokenResponse.accessToken(), token.getRefreshToken());
                })
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);
    }

    //HTTP 요청 헤더에서 토큰을 가져오는 메서드
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getPrefix())
                && bearerToken.length() > jwtProperties.getPrefix().length() + 1) {

            return bearerToken.substring(jwtProperties.getPrefix().length());
        }

        return null;
    }

    private boolean isNotRefreshToken(String token) {
        return !REFRESH_TYPE.equals(getClaims(token).get(CLAIM_TYPE, String.class));
    }
}
