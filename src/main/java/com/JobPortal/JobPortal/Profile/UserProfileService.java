package com.JobPortal.JobPortal.Profile;


import com.JobPortal.JobPortal.UserInfo;
import com.JobPortal.JobPortal.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
    public class UserProfileService {

        @Autowired
        private UserProfileREPO profileRepo;

        @Autowired
        private UserInfoRepo userRepo;

        public UserProfileResponseDTO createOrUpdateProfile(String email, UserProfileRequestDTO dto) {

            UserInfo user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserProfile profile = profileRepo.findByUser(user)
                    .orElse(new UserProfile());

            profile.setUser(user);
            profile.setSkills(dto.getSkills());
            profile.setLocation(dto.getLocation());
            profile.setPhone(dto.getPhone());
            profile.setExperience(dto.getExperience());

            profileRepo.save(profile);

            return new UserProfileResponseDTO(profile);
        }

        public UserProfileResponseDTO getMyProfile(String email) {

            UserInfo user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserProfile profile = profileRepo.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Profile not found"));

            return new UserProfileResponseDTO(profile);
        }
    }

