package team.jeonghokim.daedongyeojido.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtTokenProvider;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String STUDENT = "STUDENT";
    private static final String ADMIN = "ADMIN";
    private static final String TEACHER = "TEACHER";
    private static final String CLUB_MEMBER = "CLUB_MEMBER";
    private static final String CLUB_LEADER = "CLUB_LEADER";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/auth/logout").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/auth/reissue").permitAll()

                        // user
                        .requestMatchers(HttpMethod.GET, "/user/submission").hasAnyRole(STUDENT, CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.PATCH, "/user/decide/**").hasAnyRole(CLUB_MEMBER, CLUB_LEADER, STUDENT)
                        .requestMatchers(HttpMethod.PATCH, "/user/**").hasAnyRole(STUDENT, CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.POST, "/user/application/**").hasAnyRole(STUDENT, CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/user/application/**").hasAnyRole(STUDENT, CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.DELETE, "/user/application/**").hasAnyRole(STUDENT, CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/user/**").permitAll()

                        // admin
                        .requestMatchers("/admin/**").hasRole(ADMIN)

                        // club
                        .requestMatchers(HttpMethod.POST, "/club/member/apply").hasAnyRole(CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.GET, "/club/submission/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.POST, "/club/create/apply").hasAnyRole(STUDENT, ADMIN, TEACHER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.POST, "/club/dissolution").hasRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH, "/club/pass/**").hasRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH, "/club/**").hasAnyRole(CLUB_MEMBER)
                        .requestMatchers(HttpMethod.DELETE, "/club/member/**").hasRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.GET, "/club/alarm").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/club/**").permitAll()

                        // announcement
                        .requestMatchers(HttpMethod.POST, "/announcement").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.PATCH, "/announcement/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.DELETE, "/announcement/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/announcement/**").permitAll()

                        // schedule
                        .requestMatchers(HttpMethod.POST, "/schedule/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.PATCH, "/schedule/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/schedule/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)

                        // application
                        .requestMatchers(HttpMethod.POST, "/application/**").hasAnyRole(STUDENT, CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/application/**").hasAnyRole(STUDENT, CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.DELETE, "/application/**").hasAnyRole(STUDENT, CLUB_LEADER, CLUB_MEMBER)

                        // application-form
                        .requestMatchers(HttpMethod.GET, "/application-form/**").permitAll()
                        .anyRequest().authenticated()
                )
                .with(new SecurityFilterConfig(jwtTokenProvider, objectMapper), Customizer.withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
