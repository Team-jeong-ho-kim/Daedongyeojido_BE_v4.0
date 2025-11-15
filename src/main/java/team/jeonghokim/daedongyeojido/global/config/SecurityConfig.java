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
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**", "/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(ADMIN)
                        .requestMatchers("/club/member/apply").hasAnyRole(CLUB_MEMBER, CLUB_LEADER)
                        .requestMatchers(HttpMethod.GET, "/club/**").permitAll()
                        .requestMatchers("/club/create/apply").hasAnyRole(STUDENT, ADMIN, TEACHER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.POST, "/club/dissolution").hasRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.PATCH,"/club/**").hasAnyRole(CLUB_MEMBER)
                        .requestMatchers(HttpMethod.POST,"/application/**").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
                        .requestMatchers(HttpMethod.DELETE, "/club/member/**").hasRole(CLUB_LEADER)
                        .requestMatchers(HttpMethod.POST, "/announcement").hasAnyRole(CLUB_LEADER, CLUB_MEMBER)
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
