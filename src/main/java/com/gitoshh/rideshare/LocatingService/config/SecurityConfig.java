package com.gitoshh.rideshare.LocatingService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${server.allowed_origins}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic()
                .disable()
                .formLogin()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/locations/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter())
                .build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();
        String[] allowedOriginsArrays = allowedOrigins.split(",");

        configuration.setAllowCredentials(true);
        for (String allowedOrigin: allowedOriginsArrays) {
            configuration.addAllowedOrigin(allowedOrigin);
        }
        configuration.addAllowedHeader("*");

        String[] allowedMethods = {"OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"};
        for (String allowedMethod: allowedMethods) {
            configuration.addAllowedMethod(allowedMethod);
        }
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
