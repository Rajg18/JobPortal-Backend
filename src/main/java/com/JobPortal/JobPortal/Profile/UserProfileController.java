package com.JobPortal.JobPortal.Profile;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService profileService;

    public UserProfileController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/save")
    public ResponseEntity<UserProfileResponseDTO> saveProfile(
            @Valid @RequestBody UserProfileRequestDTO dto,
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(profileService.createOrUpdateProfile(email, dto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(profileService.getMyProfile(email));
    }
}
