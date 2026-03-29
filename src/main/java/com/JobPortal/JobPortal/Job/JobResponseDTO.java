package com.JobPortal.JobPortal.Job;

public class JobResponseDTO {

        private Long id;
        private String title;
        private String description;
        private String location;
        private String techStack;
        private int experienceRequired;
        private String companyName;
        private String postedBy;

        public JobResponseDTO(Job job) {
            this.id = job.getId();
            this.title = job.getTitle();
            this.description = job.getDescription();
            this.location = job.getLocation();
            this.techStack = job.getTechStack();
            this.experienceRequired = job.getExperienceRequired();
            this.companyName = job.getCompanyName();
            this.postedBy = job.getCreatedBy().getEmail();
        }

        // getters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    public int getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(int experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}

