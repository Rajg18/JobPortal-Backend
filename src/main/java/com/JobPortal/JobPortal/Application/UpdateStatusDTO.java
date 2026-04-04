package com.JobPortal.JobPortal.Application;

import jakarta.validation.constraints.NotBlank;
public class UpdateStatusDTO {

        @NotBlank
        private String status; // ACCEPTED / REJECTED

        // getter & setter

    public @NotBlank String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank String status) {
        this.status = status;
    }
}

