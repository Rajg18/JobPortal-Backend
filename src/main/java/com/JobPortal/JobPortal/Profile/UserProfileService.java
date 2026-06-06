package com.JobPortal.JobPortal.Profile;

import com.JobPortal.JobPortal.user.UserInfo;
import com.JobPortal.JobPortal.user.UserInfoRepo;
import com.JobPortal.JobPortal.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileREPO profileRepo;
    private final UserInfoRepo userRepo;

    public UserProfileService(UserProfileREPO profileRepo, UserInfoRepo userRepo) {
        this.profileRepo = profileRepo;
        this.userRepo    = userRepo;
    }

    public UserProfileResponseDTO createOrUpdateProfile(String email, UserProfileRequestDTO dto) {
        UserInfo user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        // Create new profile or update the existing one
        UserProfile profile = profileRepo.findByUser(user).orElse(new UserProfile());
        profile.setUser(user);
        profile.setSkills(dto.getSkills());
        profile.setLocation(dto.getLocation());
        profile.setPhone(dto.getPhone());
        profile.setExperience(dto.getExperience());

        return new UserProfileResponseDTO(profileRepo.save(profile));
    }

    public UserProfileResponseDTO getMyProfile(String email) {
        UserInfo user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        UserProfile profile = profileRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user: " + email));

        return new UserProfileResponseDTO(profile);
    }
}
