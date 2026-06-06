package com.JobPortal.JobPortal.Job;

import java.time.LocalDateTime;

public class JobResponseDTO {

    private Long          id;
    private String        title;
    private String        description;
    private String        location;
    private String        techStack;
    private int           experienceRequired;
    private String        companyName;
    private LocalDateTime createdAt;
    private String        postedBy;

    public JobResponseDTO(Job job) {
        this.id                 = job.getId();
        this.title              = job.getTitle();
        this.description        = job.getDescription();
        this.location           = job.getLocation();
        this.techStack          = job.getTechStack();
        this.experienceRequired = job.getExperienceRequired();
        this.companyName        = job.getCompanyName();
        this.createdAt          = job.getCreatedAt();
        this.postedBy           = job.getCreatedBy() != null ? job.getCreatedBy().getEmail() : null;
    }

    public Long          getId()                 { return id; }
    public String        getTitle()              { return title; }
    public String        getDescription()        { return description; }
    public String        getLocation()           { return location; }
    public String        getTechStack()          { return techStack; }
    public int           getExperienceRequired() { return experienceRequired; }
    public String        getCompanyName()        { return companyName; }
    public LocalDateTime getCreatedAt()          { return createdAt; }
    public String        getPostedBy()           { return postedBy; }
}
