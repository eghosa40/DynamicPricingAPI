package com.example.DynamicPricingAPI.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        // Bypass JWT authentication for public endpoints
        if (path.equals("/user/register") || path.equals("/user/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Proceed with normal JWT authentication logic for other endpoints
        // ... (your JWT processing code) ...

        filterChain.doFilter(request, response);
    }


}

