package com.JobPortal.JobPortal.auth;

import com.JobPortal.JobPortal.exception.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserInfoService service;
    private final JWTservice jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserInfoService service,
                          JWTservice jwtService,
                          PasswordEncoder passwordEncoder) {
        this.service = service;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            // Load user
            UserDetails userDetails = service.loadUserByUsername(authRequest.getUsername());

            // Compare password against stored BCrypt hash
            if (!passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse(401, "Invalid email or password. Please try again."));
            }

            // Build roles claim for the JWT
            String roles = userDetails.getAuthorities()
                    .stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.joining(","));

            String token = jwtService.generateToken(authRequest.getUsername(), roles);
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(401, "Invalid email or password. Please try again."));
        } catch (Exception ex) {
            // Temporary: expose exception details for debugging
            String detail = ex.getClass().getSimpleName() + ": " + ex.getMessage();
            System.err.println("[LOGIN DEBUG] " + detail);
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, detail));
        }
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
