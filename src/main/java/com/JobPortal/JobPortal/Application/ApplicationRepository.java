package com.JobPortal.JobPortal.Application;


import com.JobPortal.JobPortal.Job.Job;
import com.JobPortal.JobPortal.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    public interface ApplicationRepository extends JpaRepository<Application, Long> {

        Optional<Application> findByUserAndJob(UserInfo user, Job job);

        List<Application> findByUser(UserInfo user);

        List<Application> findByJob(Job job);

        // ── Platform-wide counts (kept for potential super-admin use) ──────────
        long countByStatus(ApplicationStatus status);

        // ── Recruiter-scoped counts ────────────────────────────────────────────
        // Counts all applications whose job was posted by the given admin email.
        long countByJob_CreatedBy_Email(String adminEmail);

        // Counts applications filtered by both owner email and application status.
        long countByJob_CreatedBy_EmailAndStatus(String adminEmail, ApplicationStatus status);
    }
