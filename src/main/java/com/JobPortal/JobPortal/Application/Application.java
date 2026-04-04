package com.JobPortal.JobPortal.Application;

import com.JobPortal.JobPortal.Job.Job;
import com.JobPortal.JobPortal.UserInfo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
    @Table(name = "applications",
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "job_id"}))

public class Application {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserInfo user;

        @ManyToOne
        @JoinColumn(name = "job_id")
        private Job job;

        private String status; // APPLIED, ACCEPTED, REJECTED

        private LocalDateTime appliedAt;

        // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}

