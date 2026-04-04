package com.JobPortal.JobPortal.Application;

public class ApplicationResponseDTO {


        private Long id;
        private String jobTitle;
        private String companyName;
        private String status;


        public ApplicationResponseDTO(Application app) {
            this.id = app.getId();
            this.jobTitle = app.getJob().getTitle();
            this.companyName = app.getJob().getCompanyName();
            this.status = app.getStatus();

        }

        // getters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

