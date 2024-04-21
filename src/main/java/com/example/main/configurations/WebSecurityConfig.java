package com.example.main.configurations;

import com.example.main.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class) // Authentication
            .authorizeHttpRequests( requests -> { // Authorization
                requests
                        .requestMatchers(
                                GET, String.format( "%s/categories/**",apiPrefix)
                        ).permitAll()
                        .requestMatchers(
                                GET, String.format( "%s/appointment_types/**", apiPrefix)
                        ).permitAll()
                        .requestMatchers(
                                GET, String.format("%s/doctors/**", apiPrefix)
                        ).permitAll()
                        .requestMatchers(
                                GET, String.format("%s/rooms/**", apiPrefix)
                        ).permitAll()
                        .requestMatchers(
                                POST, String.format("%s/users/refreshToken/**", apiPrefix)
                        ).authenticated()
                        .anyRequest()
                        .authenticated();
            });
        http.securityMatcher(String.valueOf(EndpointRequest.toAnyEndpoint()));
        return http.build();
    }
}
