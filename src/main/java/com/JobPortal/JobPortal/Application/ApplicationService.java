package com.JobPortal.JobPortal.Application;

import com.JobPortal.JobPortal.Job.Job;
import com.JobPortal.JobPortal.Job.JobREPO;
import com.JobPortal.JobPortal.user.UserInfo;
import com.JobPortal.JobPortal.user.UserInfoRepo;
import com.JobPortal.JobPortal.exception.DuplicateResourceException;
import com.JobPortal.JobPortal.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository appRepo;
    private final JobREPO jobRepo;
    private final UserInfoRepo userRepo;

    public ApplicationService(ApplicationRepository appRepo,
                               JobREPO jobRepo,
                               UserInfoRepo userRepo) {
        this.appRepo  = appRepo;
        this.jobRepo  = jobRepo;
        this.userRepo = userRepo;
    }

    // ── Apply to a job ────────────────────────────────────────────────────────

    public String applyJob(String email, Long jobId) {

        UserInfo user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (appRepo.findByUserAndJob(user, job).isPresent()) {
            throw new DuplicateResourceException("You have already applied for this job");
        }

        Application app = new Application();
        app.setUser(user);
        app.setJob(job);
        app.setStatus(ApplicationStatus.APPLIED);
        app.setAppliedAt(LocalDateTime.now());

        appRepo.save(app);
        return "Applied successfully to: " + job.getTitle();
    }

    // ── View own applications (USER) ──────────────────────────────────────────

    public List<ApplicationResponseDTO> getMyApplications(String email) {
        UserInfo user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        return appRepo.findByUser(user)
                .stream()
                .map(ApplicationResponseDTO::new)
                .toList();
    }

    // ── View applicants for a job (ADMIN) ─────────────────────────────────────

    public List<ApplicationResponseDTO> getApplicants(Long jobId) {
        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        return appRepo.findByJob(job)
                .stream()
                .map(ApplicationResponseDTO::new)
                .toList();
    }

    // ── Update application status (ADMIN) ────────────────────────────────────

    public String updateStatus(Long appId, ApplicationStatus status) {
        Application app = appRepo.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + appId));

        app.setStatus(status);
        appRepo.save(app);
        return "Application status updated to: " + status.name();
    }
}
