package com.TechnicalTest.list_service.config;

import com.TechnicalTest.list_service.service.PagerUserDetailsService;
import com.TechnicalTest.list_service.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final PagerUserDetailsService userDetailsService;


    public SecurityConfig(JwtUtil jwtUtil, PagerUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                Optional.ofNullable(request.getHeader("Authorization"))
                        .filter(h -> h.startsWith("Bearer "))
                        .map(h -> h.substring(7))
                        .flatMap(jwt -> safeExtractUsername(jwt)
                                .map(username -> new String[]{jwt, (String) username}))
                        .filter(arr -> SecurityContextHolder.getContext().getAuthentication() == null)
                        .ifPresent(arr -> {
                            String jwt = arr[0];
                            String username = arr[1];
                        });
            }

            private Optional<?> safeExtractUsername(String jwt) {
                try {
                    return Optional.ofNullable(jwtUtil.extractUsername(jwt));
                } catch (Exception e){
                    Optional.ofNullable(e.getMessage())
                            .filter(msg -> !msg.isEmpty())
                            .ifPresent(msg -> System.out.println("Jwt error: "+ msg));
                    return Optional.empty();
                }
            }
        };
    }
}
