package com.ejemplo.apirest.config;

import com.ejemplo.apirest.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    // ==========================================
    // SECURITY FILTER CHAIN
    // ==========================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // API SIN SESIONES
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // SWAGGER
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // LOGIN
                        .requestMatchers("/auth/**").permitAll()

                        // GET CLIENTES
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/clientes/**"
                        ).hasAnyRole("ADMIN", "CLIENTE")

                        // CREAR CLIENTE
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/clientes/**"
                        ).permitAll()

                        // ACTUALIZAR CLIENTE
                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/clientes/**"
                        ).hasRole("ADMIN")

                        // ELIMINAR CLIENTE
                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/clientes/**"
                        ).hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                // JWT
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }


    // ==========================================
    // PASSWORD ENCODER
    // ==========================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // ==========================================
    // AUTHENTICATION MANAGER
    // ==========================================

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        userDetailsService
                );

        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }
}