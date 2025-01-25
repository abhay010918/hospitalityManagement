package com.hotel_management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfiguration {

    private final JWTFilter jwtFilter;

    public SecurityConfiguration(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    )throws Exception
    {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF using the new API
                .cors(AbstractHttpConfigurer::disable)  // Disable CORS using the new API
                .addFilterBefore(jwtFilter,AuthorizationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll());  // Allow all requests without authentication
        return httpSecurity.build();
    }
}
