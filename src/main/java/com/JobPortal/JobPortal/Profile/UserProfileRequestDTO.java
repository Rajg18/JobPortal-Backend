package com.JobPortal.JobPortal.Profile;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserProfileRequestDTO {


        @NotBlank
        private String skills;

        @NotBlank
        private String location;

        @NotBlank
        private String phone;

        @Min(0)
        private int experience;

        // getters & setters

    public @NotBlank String getSkills() {
        return skills;
    }

    public void setSkills(@NotBlank String skills) {
        this.skills = skills;
    }

    public @NotBlank String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank String location) {
        this.location = location;
    }

    public @NotBlank String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank String phone) {
        this.phone = phone;
    }

    public @Min(0) int getExperience() {
        return experience;
    }

    public void setExperience(@Min(0) int experience) {
        this.experience = experience;
    }
}

