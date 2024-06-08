package com.example.reservationshop.security;


import com.example.reservationshop.model.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers(
                        "/auth/manager/signup",
                        "/auth/manager/signin",
                        "/auth/shop/signup",
                        "/auth/shop/signin"

                                ).permitAll()
                                .requestMatchers(HttpMethod.GET,"/shop").permitAll()
                                .requestMatchers(HttpMethod.GET,"/shop/{shopId}").permitAll()
                                .requestMatchers(HttpMethod.POST,"/shop").hasRole(Role.ROLE_MANAGER.getRole())
                                .requestMatchers(HttpMethod.PATCH,"/shop").hasRole(Role.ROLE_MANAGER.getRole())
                                .requestMatchers(HttpMethod.DELETE,"/shop").hasRole(Role.ROLE_MANAGER.getRole())
                                .requestMatchers(HttpMethod.POST,"/shop/{shopId}/reservation").hasRole(Role.ROLE_CUSTOMER.getRole())
                                .requestMatchers(HttpMethod.GET,"/shop/{shopId}/reservation").hasRole(Role.ROLE_CUSTOMER.getRole())
                                .requestMatchers(HttpMethod.PATCH,"/shop/reservation/{reservationId}").hasRole(Role.ROLE_CUSTOMER.getRole())
                                .requestMatchers(HttpMethod.POST,"/shop/review").hasRole(Role.ROLE_CUSTOMER.getRole())
                                .requestMatchers(HttpMethod.PATCH,"/shop/review").hasRole(Role.ROLE_CUSTOMER.getRole())
                                .requestMatchers(HttpMethod.DELETE,"/shop/review").hasAnyRole(Role.ROLE_CUSTOMER.getRole(),Role.ROLE_MANAGER.getRole())
                                .anyRequest().authenticated()
                ).headers(
                        headersConfigurer ->
                                headersConfigurer.frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                )
                );
        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 필터를 거치지 않는 요청 설정
     *
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
        );
    }
}
