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
                        .requestMatchers(HttpMethod.PATCH, "/user/my-info").hasAnyRole(STUDENT, CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.GET, "/user").hasAnyRole(STUDENT, CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH, "/user").hasAnyRole(STUDENT, CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH, "/user/member/decision").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.GET, "/user/submission").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.PATCH, "/user/decide/**").hasRole(STUDENT)
                        .requestMatchers(HttpMethod.GET, "/user/alarm").hasAnyRole(STUDENT, ADMIN, TEACHER, CLUB_MEMBER, CLUB_LEADER)

                        // application
                        .requestMatchers("/application/**").hasRole(STUDENT)

                        // admin
                        .requestMatchers(HttpMethod.PATCH, "/admin/club/create/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/admin/dissolution/**").hasRole(ADMIN)

                        // club
                        .requestMatchers(HttpMethod.POST, "/club/create/apply").hasAnyRole(STUDENT)
                        .requestMatchers(HttpMethod.POST, "/club/dissolution").hasRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.POST, "/club/member/apply").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/club/submission").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/club/submission/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/club/alarm").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/club/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/club/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.PATCH, "/club/pass/**").hasAnyRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.DELETE, "/club/member/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)

                        // announcement
                        .requestMatchers(HttpMethod.POST, "/announcement").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.PATCH, "/announcement/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.DELETE, "/announcement/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/announcement/**").permitAll()

                        // application-form
                        .requestMatchers(HttpMethod.POST,"/application-form").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/application-form/all/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/application-form/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/application-form/**").hasAnyRole(CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.DELETE,"/application-form/**").hasAnyRole(CLUB_MEMBER, CLUB_LEADER)

                        // schedule
                        .requestMatchers(HttpMethod.POST, "/schedule/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.PATCH, "/schedule/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.GET, "/schedule/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
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
