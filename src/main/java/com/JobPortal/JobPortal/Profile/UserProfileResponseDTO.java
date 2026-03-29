package com.JobPortal.JobPortal.Profile;

public class UserProfileResponseDTO {


        private String email;
        private String skills;
        private String location;
        private String phone;
        private int experience;

        public UserProfileResponseDTO(UserProfile profile) {
            this.email = profile.getUser().getEmail();
            this.skills = profile.getSkills();
            this.location = profile.getLocation();
            this.phone = profile.getPhone();
            this.experience = profile.getExperience();
        }

        // getters

    public String getEmail() {
        return email;
    }

    public String getSkills() {
        return skills;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public int getExperience() {
        return experience;
    }
}
