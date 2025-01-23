package com.hotel_management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    )throws Exception
    {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF using the new API
                .cors(AbstractHttpConfigurer::disable)  // Disable CORS using the new API
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll());  // Allow all requests without authentication
        return httpSecurity.build();
    }
}
