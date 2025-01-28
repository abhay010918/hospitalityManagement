package com.hotel_management.configuration;

import com.hotel_management.entity.AppUser;
import com.hotel_management.repository.AppUserRepository;
import com.hotel_management.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final AppUserRepository appUserRepository;

    public JWTFilter(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Check if the Authorization header is present and starts with "Bearer "
        String authorization = request.getHeader("Authorization");


        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(8, authorization.length()-1); // Remove the "Bearer " part
            String username = jwtService.getUsername(token); // Get the username from the token
            Optional<AppUser> byUsername = appUserRepository.findByUsername(username);
            if(byUsername.isPresent()){
                AppUser appUser = byUsername.get();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(appUser,null,null);
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}
