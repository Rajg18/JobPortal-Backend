package com.JobPortal.JobPortal.config;

import com.JobPortal.JobPortal.auth.JwtAuthFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider,
            JwtAuthFilter jwtAuthFilter) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // picks up the CorsConfigurationSource bean
            .authorizeHttpRequests(auth -> auth
                // ── Public endpoints ───────────────────────────────────────
                .requestMatchers(
                        "/auth/welcome",
                        "/auth/register",
                        "/auth/login",
                        // Swagger UI — all paths it needs to load completely
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/webjars/**").permitAll()

                // ── User-only endpoints ────────────────────────────────────
                // NOTE: roles are stored without "ROLE_" prefix, so we use
                // hasAuthority() consistently throughout — never hasRole().
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
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
