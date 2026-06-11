package com.JobPortal.JobPortal.config;

import com.JobPortal.JobPortal.auth.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter,
            CorsConfigurationSource corsConfigurationSource) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .authorizeHttpRequests(auth -> auth
                // ── Public endpoints ───────────────────────────────────────
                .requestMatchers(
                        "/auth/welcome",
                        "/auth/register",
                        "/auth/login",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/webjars/**").permitAll()

                // ── User-only endpoints ────────────────────────────────────
                .requestMatchers("/auth/user/**").hasAuthority("USER")
                .requestMatchers("/api/profile/**").hasAuthority("USER")
                .requestMatchers("/api/applications/apply/**").hasAuthority("USER")
                .requestMatchers("/api/applications/my").hasAuthority("USER")

                // ── Admin-only endpoints ───────────────────────────────────
                .requestMatchers("/auth/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/jobs/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/applications/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")

                // ── Shared endpoints (both USER and ADMIN) ─────────────────
                .requestMatchers("/api/jobs/**").hasAnyAuthority("USER", "ADMIN")

                .anyRequest().authenticated()
            )
            .sessionManagement(sess ->
                    sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
