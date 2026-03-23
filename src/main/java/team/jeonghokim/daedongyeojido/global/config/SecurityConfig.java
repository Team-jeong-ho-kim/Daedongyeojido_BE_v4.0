package team.jeonghokim.daedongyeojido.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtTokenProvider;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${security.prometheus.ip-range}")
    private String prometheusCidr;

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

                        .requestMatchers("/").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/actuator/prometheus").permitAll()

                        // auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/auth/logout").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/auth/reissue").permitAll()

                        // user
                        .requestMatchers(HttpMethod.PATCH, "/users/my-info").hasAnyRole(STUDENT, CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole(STUDENT, CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH, "/users").hasAnyRole(STUDENT, CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH, "/users/members").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.GET, "/users/submissions").hasAnyRole(STUDENT, CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH, "/users/submissions/**").hasRole(STUDENT)

                        // application
                        .requestMatchers(HttpMethod.GET, "/applications/**").hasAnyRole(STUDENT, CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.POST, "/applications/**").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.PATCH, "/applications/**").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.DELETE, "/applications/**").hasRole(STUDENT)

                        // admin
                        .requestMatchers(HttpMethod.DELETE, "/admin/dissolution/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.POST, "/admin/result-duration").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.PATCH, "/admin/result-duration/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.POST, "/admin/club-creation-form").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.POST, "/admin/teachers").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/admin/sms-histories/excel").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/admin/club-creation-form/**").hasAnyRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/admin/result-duration/**").hasAnyRole(ADMIN)

                        // teacher
                        .requestMatchers(HttpMethod.GET, "/teachers").hasAnyRole(STUDENT, ADMIN)
                        .requestMatchers(HttpMethod.GET, "/teachers/my-info").hasAnyRole(TEACHER)

                        // club
                        .requestMatchers(HttpMethod.POST, "/clubs/applications").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.POST, "/clubs/dissolution").hasRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.POST, "/clubs/members").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/clubs/submissions").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/clubs/submissions/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/clubs/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/clubs/interviews/**").hasAnyRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH, "/clubs/pass/**").hasAnyRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH, "/clubs/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.DELETE, "/clubs/members/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)

                        // announcement
                        .requestMatchers(HttpMethod.POST, "/announcements").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.PATCH, "/announcements/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.DELETE, "/announcements/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/announcements/clubs/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER, STUDENT)
                        .requestMatchers(HttpMethod.GET, "/announcements/**").permitAll()

                        // application-form
                        .requestMatchers(HttpMethod.POST,"/application-forms").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/application-forms/clubs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/application-forms/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/application-forms/**").hasAnyRole(CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.DELETE,"/application-forms/**").hasAnyRole(CLUB_MEMBER, CLUB_LEADER)

                        // schedule
                        .requestMatchers(HttpMethod.POST, "/schedules/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.PATCH, "/schedules/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/schedules/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)

                        // club-creation-application
                        .requestMatchers(HttpMethod.GET, "/club-creation-applications/me").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.PATCH, "/club-creation-applications/**").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.POST, "/club-creation-applications/**").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.GET, "/club-creation-applications").hasAnyRole(ADMIN, TEACHER)
                        .requestMatchers(HttpMethod.GET, "/club-creation-applications/**").hasAnyRole(ADMIN, TEACHER)
                        .requestMatchers(HttpMethod.PUT, "/club-creation-applications/**").hasAnyRole(ADMIN, TEACHER)

                        // result-duration
                        .requestMatchers(HttpMethod.GET, "/result-duration").permitAll()

                        // alarm
                        .requestMatchers(HttpMethod.GET, "/alarms/clubs").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/alarms/users").hasAnyRole(STUDENT, CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/alarms/admins").hasAnyRole(ADMIN)

                        // file
                        .requestMatchers(HttpMethod.POST, "/files").hasAnyRole(ADMIN, STUDENT, CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.GET, "/files/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/files/**").hasAnyRole(ADMIN, STUDENT, CLUB_MEMBER, CLUB_LEADER)

                        // monitoring
                        .requestMatchers("/actuator/prometheus")
                        .access(new WebExpressionAuthorizationManager(
                                "hasIpAddress('" + prometheusCidr + "')"
                        ))
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().authenticated()
                )
                .with(new SecurityFilterConfig(jwtTokenProvider, objectMapper), Customizer.withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(
                List.of(
                        "http://localhost:*",
                        "https://dsm.daedongyeojido.site",
                        "https://student.daedongyeojido.site",
                        "https://admin.daedongyeojido.site",
                        "https://teacher.daedongyeojido.site"
                )
        );
        configuration.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
