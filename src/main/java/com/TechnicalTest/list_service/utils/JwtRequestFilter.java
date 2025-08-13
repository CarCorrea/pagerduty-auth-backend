package com.TechnicalTest.list_service.utils;

import com.TechnicalTest.list_service.service.PagerUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final PagerUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(PagerUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        String username = Optional.ofNullable(authHeader)
                .filter(h -> h.startsWith("Bearer"))
                .map(h -> h.substring(7))
                .map(token -> {
                    try {
                        return jwtUtil.extractUsername(token);
                    } catch (Exception e){
                        return e.getMessage();
                    }
                })
                .orElse("Internal error");
    }
}
