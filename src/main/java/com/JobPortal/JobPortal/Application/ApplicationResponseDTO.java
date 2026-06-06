package com.JobPortal.JobPortal.Application;

public class ApplicationResponseDTO {

    private Long   id;
    private String jobTitle;
    private String companyName;
    private String status;
    private String appliedAt;

    public ApplicationResponseDTO(Application app) {
        this.id          = app.getId();
        this.jobTitle    = app.getJob().getTitle();
        this.companyName = app.getJob().getCompanyName();
        this.status      = app.getStatus().name();
        this.appliedAt   = app.getAppliedAt().toString();
    }

    public Long   getId()          { return id; }
    public String getJobTitle()    { return jobTitle; }
    public String getCompanyName() { return companyName; }
    public String getStatus()      { return status; }
    public String getAppliedAt()   { return appliedAt; }
}
