package com.JobPortal.JobPortal.Job;

import com.JobPortal.JobPortal.user.UserInfo;
import jakarta.persistence.*;

import java.time.LocalDateTime;


    @Entity
    @Table(name = "jobs")
public class Job {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        @Column(length = 2000)
        private String description;

        private String location;

        private String techStack;

        private int experienceRequired;

        private String companyName;

        private LocalDateTime createdAt;

        @ManyToOne
        @JoinColumn(name = "admin_id")
        private UserInfo createdBy;

        // getters & setters


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

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public UserInfo getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(UserInfo createdBy) {
            this.createdBy = createdBy;
        }
    }

