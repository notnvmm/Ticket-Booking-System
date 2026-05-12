package org.example.ticket_booking.config;

import lombok.RequiredArgsConstructor;
import org.example.ticket_booking.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor

public class SecurityConfig {
     private final JwtAuthFilter jwtAuthFilter;

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 //disable csrf - not required for REST APIs
                 .csrf(csrf -> csrf.disable())

                 //don't store sessions - JWT is stateless
                 .sessionManagement(session ->
                         session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                 //register/login are public, everything else needs JWT
                 .authorizeHttpRequests(auth ->
                         auth.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())

                 //plug in our JWT filter before Spring Security's default login filter
                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
     }

     //hash password using BCrypt
     @Bean
    public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
     }

     //authenticate username+password during login
     @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
         return config.getAuthenticationManager();
     }
}
