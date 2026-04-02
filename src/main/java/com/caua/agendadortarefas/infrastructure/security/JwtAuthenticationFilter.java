package com.caua.agendadortarefas.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // DEBUG 1: See if the header actually arrived at the Microservice
        System.out.println("MS DEBUG - Authorization Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                // DEBUG 2: Check if extraction works
                String email = jwtUtil.extrairEmailToken(token);
                System.out.println("MS DEBUG - Token Validated for User: " + email);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            email, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // DEBUG 3: This is the most important log!
                System.err.println("MS DEBUG - JWT VALIDATION FAILED: " + e.getMessage());
                // If it prints "SignatureException", the keys don't match.
            }
        } else {
            System.out.println("MS DEBUG - No valid Bearer token found in header");
        }
        filterChain.doFilter(request, response);
    }
}