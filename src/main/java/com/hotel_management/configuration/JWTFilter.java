package com.hotel_management.configuration;

import com.hotel_management.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Check if the Authorization header is present and starts with "Bearer "
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(8, authorization.length()-1); // Remove the "Bearer " part
            String username = jwtService.getUsername(token); // Get the username from the token
            System.out.println(username);
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}
