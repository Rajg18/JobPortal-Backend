package com.JobPortal.JobPortal.Job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

public class JobRequestDTO {


        @NotBlank
        private String title;

        @NotBlank
        private String description;

        @NotBlank
        private String location;

        private String techStack;

        @Min(0)
        private int experienceRequired;

        @NotBlank
        private String companyName;

        // getters & setters


    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    public @NotBlank String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank String location) {
        this.location = location;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @Min(0) int getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(@Min(0) int experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public @NotBlank String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(@NotBlank String companyName) {
        this.companyName = companyName;
    }
}

