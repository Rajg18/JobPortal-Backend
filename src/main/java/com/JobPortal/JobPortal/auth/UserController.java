package com.JobPortal.JobPortal.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserInfoService service;
    private final JWTservice jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserInfoService service,
                          JWTservice jwtService,
                          AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Job Portal API. Register at POST /auth/register.";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(service.addUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            // Collect all authorities (e.g. "ADMIN" or "USER") into a comma-separated string
            // and embed them as the "roles" claim inside the JWT so the frontend can read them.
            String roles = authentication.getAuthorities()
                    .stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.joining(","));

            String token = jwtService.generateToken(authRequest.getUsername(), roles);
            return ResponseEntity.ok(new AuthResponse(token));
        }

        throw new UsernameNotFoundException("Invalid credentials");
    }

    @GetMapping("/user/profile")
    public ResponseEntity<String> userProfile(Authentication authentication) {
        return ResponseEntity.ok("Hello, " + authentication.getName() + " [USER]");
    }

    @GetMapping("/admin/profile")
    public ResponseEntity<String> adminProfile(Authentication authentication) {
        return ResponseEntity.ok("Hello, " + authentication.getName() + " [ADMIN]");
    }
}
