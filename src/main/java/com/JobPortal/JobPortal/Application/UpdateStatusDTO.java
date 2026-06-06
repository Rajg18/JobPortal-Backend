package com.JobPortal.JobPortal.Application;

import jakarta.validation.constraints.NotNull;

public class UpdateStatusDTO {

    @NotNull(message = "Status is required")
    private ApplicationStatus status; // ACCEPTED or REJECTED

    public ApplicationStatus getStatus()                        { return status; }
    public void               setStatus(ApplicationStatus status) { this.status = status; }
}
